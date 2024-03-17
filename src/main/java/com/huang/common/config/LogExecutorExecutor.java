package com.huang.common.config;//package com.common.config;
//
//import com.alibaba.ttl.threadpool.TtlExecutors;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
///**
// * @Classname LogExecutorConfig
// * @CreatedDate 2024/3/14 17:04
// * @Author Huang
// */
//public class LogExecutorExecutor implements Executor {
//
//    private Executor executor;
//
//    public LogExecutorExecutor(){
//        ThreadPoolTaskExecutor executor1 = new ThreadPoolTaskExecutor();
//        executor1.setCorePoolSize(2);
//        executor1.setMaxPoolSize(20);
//        executor1.setQueueCapacity(2000);
//        executor1.setKeepAliveSeconds(60);
//        executor1.setThreadNamePrefix("log-thread");
//        executor1.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
//        executor1.initialize();
//        executor = TtlExecutors.getTtlExecutor(executor1);
//    }
//    /**
//     * Executes the given command at some time in the future.  The command
//     * may execute in a new thread, in a pooled thread, or in the calling
//     * thread, at the discretion of the {@code Executor} implementation.
//     *
//     * @param command the runnable task
//     * @throws RejectedExecutionException if this task cannot be
//     *                                    accepted for execution
//     * @throws NullPointerException       if command is null
//     */
//    @Override
//    public void execute(Runnable command) {
//        executor.execute(command);
//    }
//}
