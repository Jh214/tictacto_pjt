package toy.tictacto_pjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TictactoPjtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TictactoPjtApplication.class, args);
    }

}
