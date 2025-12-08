package reportshell.samples.exporters;

import com.bivektor.reportshell.core.export.ConfigurableExporterFactoryRegistry;
import com.bivektor.reportshell.spring.config.ReportShellConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class ReportShellConfig implements ReportShellConfigurer {
  @Override
  public void registerExporterFactories(@NonNull ConfigurableExporterFactoryRegistry registry) {
    registry.registerExporterFactory("rtf", new RtfExporterFactory());
  }
}
