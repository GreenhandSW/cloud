package io.github.greenhandsw.payeureka;

import io.github.greenhandsw.split.DataSourceInterceptor;
import io.github.greenhandsw.split.DynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EntityScan("io.github.greenhandsw.common.entity")
@Import({DynamicDataSource.DataSourceConfig.class, DataSourceInterceptor.class})
@EnableDiscoveryClient
public class PayEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayEurekaApplication.class, args);
    }

}
