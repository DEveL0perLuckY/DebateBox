package com.debate.box.debate_box_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DebateBoxWebApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DebateBoxWebApplication.class, args);
        System.out.println("web application");
    }

}
