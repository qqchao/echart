package com.example.testdemo.controller;

import com.example.testdemo.service.IEchartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Configurable
public class EchartController {
    @Autowired
    private IEchartService iEchartService;

    @RequestMapping("/getCoordinate")
    public List<Map<String, Object>> getCoordinate(){
        return iEchartService.getCoordinate();
    }

    @RequestMapping("/")
    public String sayHello(){
        return "Hello !";
    }
}
