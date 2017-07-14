package com.tasosmartidis.caching_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.tasosmartidis.caching_demo"})
public class CacheDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApp.class, args);
    }
}
