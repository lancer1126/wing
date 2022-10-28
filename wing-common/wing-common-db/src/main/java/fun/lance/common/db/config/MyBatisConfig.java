package fun.lance.common.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"fun.lance.**.mapper"})
public class MyBatisConfig {
}
