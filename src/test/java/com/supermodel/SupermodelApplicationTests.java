package com.supermodel;

import com.supermodel.model.Supermodel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SupermodelApplicationTests {

	@Test
	void supermodelExists() {
		Supermodel adriana = new Supermodel();
		assertEquals(null, adriana.getName());
	}

}
