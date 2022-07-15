package com.zx.hoperun.zt;

import com.zx.hoperun.zt.task.StatTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
// 开启服务注册与发现
@EnableDiscoveryClient
@EnableFeignClients
public class ZtApplication {

    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext context = SpringApplication.run(ZtApplication.class, args);
        StatTask statTask = context.getBean(StatTask.class);  // 获取逻辑入口类的实例
        statTask.doSomething();
    }

}
