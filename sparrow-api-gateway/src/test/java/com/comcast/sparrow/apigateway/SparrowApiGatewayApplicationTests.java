package com.comcast.sparrow.apigateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.comcast.sparrow.apigateway.SparrowApiGatewayApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SparrowApiGatewayApplication.class)
@WebAppConfiguration
public class SparrowApiGatewayApplicationTests {

	@Test
	public void contextLoads() {
	}

}
