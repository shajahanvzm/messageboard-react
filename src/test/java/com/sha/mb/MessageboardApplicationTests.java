package com.sha.mb;

import com.sha.mb.controller.MessageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MessageboardApplicationTests {

	@Autowired
	MessageController messageController;

	@Test
	void contextLoads() {
		assertThat(messageController).isNotNull();
	}

}
