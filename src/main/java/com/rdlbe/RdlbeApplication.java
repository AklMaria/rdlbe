package com.rdlbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.rdlbe.application.business.internal.domains")
public class RdlbeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RdlbeApplication.class, args);
    }

}
