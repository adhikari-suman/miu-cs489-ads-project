package edu.miu.cs489.adswebapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AdswebappApplication{

    public static void main(String[] args) {
        SpringApplication.run(AdswebappApplication.class, args);
    }

}
