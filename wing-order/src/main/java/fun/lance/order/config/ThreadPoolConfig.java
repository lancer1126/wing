package fun.lance.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    @Value("${spring.application.name}")
    private String appName;

    public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CPU_NUM);
        taskExecutor.setMaxPoolSize(CPU_NUM * 2);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.setThreadNamePrefix(appName + "-thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
