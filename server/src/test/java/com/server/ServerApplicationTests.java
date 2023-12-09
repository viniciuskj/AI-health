package com.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertNotNull("Contexto carregado com sucesso.");
	}

}
