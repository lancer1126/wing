package fun.lance.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fun.lance")
public class WingBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(WingBizApplication.class, args);
    }

}
