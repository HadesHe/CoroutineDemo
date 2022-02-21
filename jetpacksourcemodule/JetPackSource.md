# JetPackSource
> 用于分析 JectPack 库源码分析

## WorkManager 源码分析
- [WorkManager源码分析](https://mp.weixin.qq.com/s/9vTSqUXB9esDRAikWoMnwg)
- [百度脑图地址](https://naotu.baidu.com/home/af9a29a83b0d410ea9f7a18aaea520d6)
### 特点
1. 任务一定会被执行，使用本地数据库，记录任务信息及任务状态，以确保在设备重启的情况下，依旧可以执行
2. 合理使用设备资源
### 适用场景
1. 可延迟执行的任务
- 特定条件才会执行的任务
- 用户无感知或可延迟感知的任务
2. 定期重复性任务，但对时效性要求不高的
3. 退出应用后还应继续执行的未完成任务