package fun.lance.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WingUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WingUserApplication.class, args);
    }

}
