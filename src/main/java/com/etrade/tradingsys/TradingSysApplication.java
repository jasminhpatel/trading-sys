package com.etrade.tradingsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradingSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingSysApplication.class, args);
    }

}
