package edu.miu.cs489.adswebapp;

import edu.miu.cs489.adswebapp.model.Address;
import edu.miu.cs489.adswebapp.model.Patient;
import edu.miu.cs489.adswebapp.respository.PatientRepository;
import edu.miu.cs489.adswebapp.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@RequiredArgsConstructor
public class AdswebappApplication{

    public static void main(String[] args) {
        SpringApplication.run(AdswebappApplication.class, args);
    }

}
