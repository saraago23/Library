package com.project.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    @Autowired
    PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.err.println(encoder.encode("admin123"));
    }
}
