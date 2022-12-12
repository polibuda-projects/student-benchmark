package com.example.studentbenchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StudentBenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentBenchmarkApplication.class, args);
    }

}

