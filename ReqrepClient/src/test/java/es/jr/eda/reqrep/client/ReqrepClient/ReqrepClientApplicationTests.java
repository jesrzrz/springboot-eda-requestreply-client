package es.jr.eda.reqrep.client.ReqrepClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.jr.eda.reqrep.client.ReqrepClient.service.KafkaService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ReqrepClientApplicationTests {

	@Autowired KafkaService kafkaService;		
	
	@Test
	void contextLoads() throws Exception {
		
		String request = "abcd123";
		String mustResponse = "321dcba";
		
		Object sendReply = kafkaService.kafkaRequestReply(request);
		String responseString = String.valueOf(sendReply);
		
		assertEquals(mustResponse, responseString);
		log.info("Request message: {}, Response message: {}", request, responseString);
	}

}
