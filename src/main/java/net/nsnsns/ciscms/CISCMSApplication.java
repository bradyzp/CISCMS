package net.nsnsns.ciscms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CISCMSApplication {
    public static void main(String[] args) {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        SpringApplication app = new SpringApplication(loader, CISCMSApplication.class);

        app.run();
    }
}
