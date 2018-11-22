package ru.mike.contact.list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"ru.mike.contact.list.config"})
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
