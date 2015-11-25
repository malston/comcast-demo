package com.comcast.sparrow.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.comcast.sparrow.web.SparrowWebApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SparrowWebApplication.class)
@WebAppConfiguration
public class SparrowWebApplicationTests {

	@Test
	public void contextLoads() {
	}

}
