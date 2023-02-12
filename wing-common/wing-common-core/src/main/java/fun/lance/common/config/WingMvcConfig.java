package fun.lance.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WingMvcConfig implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        return new WingLocaleResolver();
    }
}
