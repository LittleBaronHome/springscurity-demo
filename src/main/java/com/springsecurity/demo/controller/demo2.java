package com.springsecurity.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo2")
public class demo2 {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "hello demo2 " + name;
    }
}
