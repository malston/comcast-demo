package com.comcast.sparrow.recommendations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.comcast.sparrow.recommendations.repositories")
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SparrowRecommendationsApplication extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SparrowRecommendationsApplication.class, args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/recommendations/**").authenticated()
                .antMatchers(HttpMethod.POST,"/people/**").authenticated()
                .antMatchers(HttpMethod.POST,"/movie/**").authenticated()
                .antMatchers(HttpMethod.GET,"/does/**/").authenticated()
                .and().authorizeRequests()
                .anyRequest().permitAll();
    }
}
