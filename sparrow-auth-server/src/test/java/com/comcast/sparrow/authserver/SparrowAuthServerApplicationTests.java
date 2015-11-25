package com.comcast.sparrow.authserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.comcast.sparrow.authserver.SparrowAuthServerApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SparrowAuthServerApplication.class)
@WebAppConfiguration
public class SparrowAuthServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
