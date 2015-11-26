package com.comcast.sparrow.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableZuulProxy
public class SparrowApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowApiGatewayApplication.class, args);
	}

}
