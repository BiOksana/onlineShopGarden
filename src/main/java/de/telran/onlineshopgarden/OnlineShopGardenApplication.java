package de.telran.onlineshopgarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineShopGardenApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopGardenApplication.class, args);
    }

}
