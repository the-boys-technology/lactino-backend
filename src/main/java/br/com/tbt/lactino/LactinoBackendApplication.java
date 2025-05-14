package br.com.tbt.lactino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LactinoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LactinoBackendApplication.class, args);
    }

}
