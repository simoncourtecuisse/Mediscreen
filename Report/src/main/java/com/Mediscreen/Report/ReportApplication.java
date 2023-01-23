package com.Mediscreen.Report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.Mediscreen.Report")
@SpringBootApplication
public class ReportApplication {

    public static void main(String[] args) { SpringApplication.run(ReportApplication.class, args); }

}
