package com.example.runnerz;


import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {

  
    public String getWelcomeMsg() {
        return "Welcome to Spring Boot Application";
    }

}
