package io.github.greenhandsw.ordereurekafeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderEurekaFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderEurekaFeignApplication.class, args);
	}

}
