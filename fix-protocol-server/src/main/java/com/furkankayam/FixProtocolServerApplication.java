package com.furkankayam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.furkankayam.config")
public class FixProtocolServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixProtocolServerApplication.class, args);
    }
}
