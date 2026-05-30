package com.furkankayam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.furkankayam.config")
public class FixProtocolClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixProtocolClientApplication.class, args);
    }
}
