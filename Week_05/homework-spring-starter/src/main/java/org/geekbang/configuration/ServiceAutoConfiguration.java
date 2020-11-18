package org.geekbang.configuration;

import org.geekbang.service.MyService;
import org.geekbang.service.impl.DefaultMyService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class ServiceAutoConfiguration {

    @Bean
    public MyService myService() {
        return new DefaultMyService();
    }
}
