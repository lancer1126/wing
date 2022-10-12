package fun.lance.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fun.lance")
public class WingUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WingUserApplication.class, args);
    }

}
