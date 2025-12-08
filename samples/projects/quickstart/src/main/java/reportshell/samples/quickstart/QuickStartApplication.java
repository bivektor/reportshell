package reportshell.samples.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import reportshell.samples.common.SamplesCommonConfiguration;

@SpringBootApplication
@Import(SamplesCommonConfiguration.class)
public class QuickStartApplication {
  public static void main(String[] args) {
		SpringApplication.run(QuickStartApplication.class, args);
  }
}
