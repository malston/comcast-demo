package com.comcast.sparrow.reviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.comcast.sparrow.reviews.SparrowReviewsApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SparrowReviewsApplication.class)
@WebAppConfiguration
public class SparrowReviewsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
