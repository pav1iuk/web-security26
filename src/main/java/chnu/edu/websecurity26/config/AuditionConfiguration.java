package chnu.edu.websecurity26.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/*
  @author   Pavliuk
  @project   web-security26
  @class  AuditionConfiguration
  @version  1.0.0 
  @since 04.10.2026 - 22.42
*/
@Configuration
@EnableMongoAuditing
public class AuditionConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
