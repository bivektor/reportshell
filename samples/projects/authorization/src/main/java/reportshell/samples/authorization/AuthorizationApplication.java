package reportshell.samples.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import reportshell.samples.common.SamplesCommonConfiguration;

@SpringBootApplication
@Import(SamplesCommonConfiguration.class)
public class AuthorizationApplication {
  public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
  }
}
