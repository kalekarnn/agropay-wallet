package com.agropay.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class WalletApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WalletApplication.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(s -> System.out.println(s));
    }
}
