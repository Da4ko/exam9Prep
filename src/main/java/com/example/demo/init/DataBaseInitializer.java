package com.example.demo.init;


import com.example.demo.service.StyleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {
    private final StyleService styleService;

    public DataBaseInitializer(StyleService styleService) {
        this.styleService = styleService;
    }

    @Override
    public void run(String...args) throws Exception{
        styleService.initStyles();
    }
}
