package reportshell.samples.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reportshell.samples.common.web.RestExceptionHandler;

@Configuration
public class SamplesCommonConfiguration {
  @Bean
  public RestExceptionHandler restExceptionHandler() {
    return new RestExceptionHandler();
  }
}
