package com.huami.coroutinedemo.ext

import androidx.work.ListenableWorker
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.instance.newInstance
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.bind

/**
 * @date 2021/12/10
 * @author hezhanghe@zepp.com
 * @desc
 */
inline fun <reified T : ListenableWorker> Module.work(
    qualifier: Qualifier = named<T>(),
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return factory(qualifier, definition).bind(ListenableWorker::class)
}

@KoinReflectAPI
inline fun <reified T : ListenableWorker> Module.work(
    qualifier: Qualifier = named<T>(),
): Pair<Module, InstanceFactory<*>> {
    return factory(qualifier) { newInstance<T>(it) }.bind(ListenableWorker::class)
}