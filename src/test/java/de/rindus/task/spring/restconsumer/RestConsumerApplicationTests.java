package de.rindus.task.spring.restconsumer;

import static org.assertj.core.api.Assertions.assertThat;

import de.rindus.task.spring.restconsumer.service.RestService;
import de.rindus.task.spring.restconsumer.web.rest.ApplicationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class RestConsumerApplicationTests {

	@Autowired
	private ApplicationController applicationController;
	
	@Autowired
	private RestService restService;
	@Autowired
	private WebClient webClient;
	
	@Test
	void contextLoads() {
		assertThat(applicationController).isNotNull();
		assertThat(restService).isNotNull();
		assertThat(webClient).isNotNull();
	}

}
