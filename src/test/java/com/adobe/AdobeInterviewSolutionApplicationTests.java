package com.adobe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.adobe.configuration.AdobeInterviewSolutionApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdobeInterviewSolutionApplication.class)
@WebAppConfiguration
public class AdobeInterviewSolutionApplicationTests {

	@Test
	public void contextLoads() {
	}

}
