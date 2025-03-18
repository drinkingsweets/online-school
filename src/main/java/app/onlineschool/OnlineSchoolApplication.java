package app.onlineschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnlineSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineSchoolApplication.class, args);
    }

}
