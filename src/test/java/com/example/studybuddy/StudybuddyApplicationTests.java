package com.example.studybuddy;

import com.example.studybuddy.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudybuddyApplicationTests {

	@Autowired
	TestRestTemplate testTemplate;


	@Test
	void shouldCreateAUser(){
		Users newUser = new Users();
		ResponseEntity<Void> createResponse = testTemplate.postForEntity("/users", newUser, Void.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewUser = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = testTemplate.getForEntity(locationOfNewUser, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
