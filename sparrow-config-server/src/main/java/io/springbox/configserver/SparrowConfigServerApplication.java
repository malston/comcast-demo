package io.springbox.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SparrowConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowConfigServerApplication.class, args);
    }
}
