package com.example.natwestassignment;

import com.example.natwestassignment.controller.StudentController;
import com.example.natwestassignment.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class NatwestAssignmentApplicationTests {

	@Autowired
	private StudentController controller;

	@Autowired
	private StudentService service;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
	}

}
