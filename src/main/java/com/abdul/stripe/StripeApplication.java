package com.abdul.stripe;

import com.abdul.toolkit.SecurityApp;
import com.abdul.toolkit.UtilsApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(
        {SecurityApp.class, UtilsApp.class}
)
public class StripeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StripeApplication.class, args);
    }

}
