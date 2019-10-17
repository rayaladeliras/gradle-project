package com.ritxar.gradleproject;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GradleProjectApplicationTests {

	@Autowired PersonPrinter personPrinter;

	@Test
	void contextLoads() {
		String persons = this.personPrinter.print();
		BDDAssertions.then(persons).isEqualTo("marcin\njohn");
	}

}
