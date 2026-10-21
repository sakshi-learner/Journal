package com.sakshi.journal.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/heath-check")
    public  String heathCheck(){
        return "Ok";
    }



}
