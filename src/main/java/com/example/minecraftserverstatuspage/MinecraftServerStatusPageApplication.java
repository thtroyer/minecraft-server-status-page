package com.example.minecraftserverstatuspage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MinecraftServerStatusPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinecraftServerStatusPageApplication.class, args);
    }

}
