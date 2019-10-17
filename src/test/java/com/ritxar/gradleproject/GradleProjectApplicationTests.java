package com.ritxar.gradleproject;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import io.restassured.RestAssured;
import org.assertj.core.api.BDDAssertions;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class GradleProjectApplicationTests {
	@ClassRule
	public WireMockClassRule wireMockRule = new WireMockClassRule(options().port(8083));

	@Autowired PersonService personService;

	@Test
	public void should_get_two_people() {
		List<Person> people = this.personService.people();
		BDDAssertions.then(people).hasSize(2);
	}

	@Before
	public void setup() {
		wireMockRule.stubFor(WireMock.get("/person/1")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type","applicatioN/json")
						.withBody("{\"name\":\"m1\"}")));
		wireMockRule.stubFor(WireMock.get("/person/2")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type","applicatioN/json")
						.withBody("{\"name\":\"m2\"}")));
	}

	@Test
	public void should_get_two_people_using_rest_assured() {
		RestAssured.given()
				.baseUri("http://localhost:8083")
				.when()
				.get("/person/1")
				.then()
				.statusCode(200)
				.contentType("application/json")
				.body("name", Matchers.equalTo("m1"));
	}

}
