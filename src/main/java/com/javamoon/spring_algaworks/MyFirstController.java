package com.javamoon.spring_algaworks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyFirstController {
    
    @GetMapping("help")
    @ResponseBody
    public String help() {
        return "heelp!!!";
    }
}
