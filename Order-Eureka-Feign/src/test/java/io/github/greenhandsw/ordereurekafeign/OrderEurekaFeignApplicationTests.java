package io.github.greenhandsw.ordereurekafeign;

import io.github.greenhandsw.ordereurekafeign.controller.OrderController;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient

class OrderEurekaFeignApplicationTests {


	@Autowired
	private WebTestClient webTestClient;

	@RepeatedTest(10)
	public void test(RepetitionInfo repetitionInfo){
		int delay=1+(repetitionInfo.getCurrentRepetition()%2);
		webTestClient.post()
				.uri("/customer/order/timeout")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(1000), Long.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void contextLoads() {
	}

}
