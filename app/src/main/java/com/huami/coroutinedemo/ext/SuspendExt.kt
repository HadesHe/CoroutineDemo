package com.huami.coroutinedemo.ext

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.yield
import java.util.concurrent.atomic.AtomicReference

/**
 * @date 2022/1/17
 * @author hezhanghe@zepp.com
 * @desc @URL：https://gist.github.com/objcode/7ab4e7b1df8acd88696cb0ccecad16f7#file-concurrencyhelpers-kt-L19
 *      @URL :https://github.com/himanshufi/kotlinextensions/blob/master/customktx/src/main/java/com/himanshu/customktx/utils/ControlledRunner.kt
 *  suspend 方法控制类，用于处理相同 suspend 方法多次运行时的场景
 *
 * 调用 joinPreviousOrReturn，后续 suspend 方法的执行会被丢弃，只返回第一个 suspend 执行结果
 * 适用于网络请求去重操作
 *
 * 调用 cancelPreviousThenReturn ，后续 suspend 方法会执行，前置 suspend 方法会被丢弃
 * 适用于排序与列表过滤，后续操作会覆盖前置操作结果，且前置操作不会对后续操作结果产生影响
 *
 * 方法原理： async 及 await() 与 cancel() 执行与取消操作需要主动执行
 */
class ControlledRunner<T> {

    /**
     * 利用 async 方法返回的 Deferred 对象记录当前执行的 suspend 方法
     *
     * 使用 AtomicReference 确保 Dispatchers.Default 及 Dispatchers.Main 更新操作的线程安全
     *
     */
    private val activeTask = AtomicReference<Deferred<T>?>(null)

    /**
     *  取消所有前置未完成的 Deferred
     *
     * ```
     *  class Products{
     *      val controlledRunner = ControlledRunner<Product>()
     *
     *      fun sortAscending():List<Product>{
     *          return controlledRunner.cancelPreviousThenReturn{ dao.loadSortedAscending() }
     *      }
     *
     *      fun sortDescending():List<Product>{
     *          return controlledRunner.cancelPreviousThenReturn { dao.loadSortedDescending() }
     *      }
     *  }
     * ```
     *  @param block the code to run after previous work is cancelled
     *  @return the result of block , if this call was not cancelled prior to returning
     */
    suspend fun cancelPreviousThenReturn(block: suspend () -> T): T {
//        如果已有任务执行，则取消
        activeTask.get()?.cancelAndJoin()

//
        return coroutineScope {
            val newTask = async(start = CoroutineStart.LAZY) {
                block()
            }

//            任务完成后，将当前任务置空
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

//            Kotlin 中 val 只会被赋值一次，即使它在循环 while(true) 里面
            val result: T
            while (true) {
                if (!activeTask.compareAndSet(null, newTask)) {
//                    如果当前任务不为 Null 且不为 newTask ，则取消当前任务
                    activeTask.get()?.cancelAndJoin()
                    yield()
                } else {
                    result = newTask.await()
                    break
                }
            }

            result
        }

    }

    /**
     * 仅执行第一条 block 代码块，并返回结果
     *
     * 当多个协程同时调用 joinPreviousOrReturn
     *
     * class Products {
     *      val controlledRunner = ControlledRunner<Product>()
     *
     *      fun fetchProducts():List<Product> {
     *          return controlledRunner.joinPreviousOrRun {
     *              val results = api.fecthProducts()
     *              dao.insert(results)
     *              results
     *          }
     *      }
     * }
     *
     */
    suspend fun joinPreviousOrReturn(block: suspend () -> T): T {
        activeTask.get()?.let {
            return it.await()
        }

        return coroutineScope {
            val newTask = async(start = CoroutineStart.LAZY) {
                block()
            }
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }
            val result: T
            while (true) {
                if (activeTask.compareAndSet(null, newTask).not()) {
                    val currentTask = activeTask.get()
                    if (currentTask != null) {
                        newTask.cancel()
                        result = currentTask.await()
                        break
                    } else {
                        yield()
                    }
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }
}

/**
 * 同时只会有单个 suspend 操作执行
 */
class SingleSuspend {

    private val mutex = Mutex()

    suspend fun <T> afterPrevious(block: suspend () -> T): T {
        mutex.withLock {
            return block()
        }
    }

}