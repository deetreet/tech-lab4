package com.example.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.example.Application.Dto"})/*, "com.example.Application.Controller", "com.example.Application.Services", "com.example.Application.Repositories", "com.example.Application.Models"})*/
/*
@EnableJpaRepositories(basePackages = {"com.example.Application.Repositories"})
@EntityScan(basePackages = {"com.example.ApplicationDataLayer.Models"})
@ComponentScan(basePackages = {"com.example.Application.Services", "com.example.Application.Dto"})
*/
public class ControllerApplication {
    public static void main(String[] args){
        SpringApplication.run(ControllerApplication.class, args);
    }
}
