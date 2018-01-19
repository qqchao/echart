package com.example.testdemo.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class EChartConfigurations {
    @Value("${file.path}")
    private String path;

    public String getPath(){
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
