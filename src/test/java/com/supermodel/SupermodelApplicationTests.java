package com.supermodel;

import com.supermodel.model.Supermodel;
import com.supermodel.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SupermodelApplicationTests {

	@Autowired
	UserService userService;

	@Test
	void supermodelExists() {
		Supermodel adriana = new Supermodel();
		assertEquals(null, adriana.getName());
	}

	@Test
	void userDoesntExistReturnsNegativeOne(){
		assertEquals(null, userService.findByUsername("Joe"));
	}

}
