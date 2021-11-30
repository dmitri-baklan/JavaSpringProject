package com.baklan.periodicals;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.aspectj.weaver.ast.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeriodicalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeriodicalsApplication.class, args);
    }

}
