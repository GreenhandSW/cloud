package io.github.greenhandsw.payeureka;

import io.github.greenhandsw.split.DataSourceInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DataSourceInterceptor.class)
@EntityScan("io.github.greenhandsw.core.entity")
@EnableDiscoveryClient
public class PayEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayEurekaApplication.class, args);
    }

}