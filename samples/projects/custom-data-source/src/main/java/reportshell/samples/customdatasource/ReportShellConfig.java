package reportshell.samples.customdatasource;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportShellConfig {

  @Bean(name = "dataSources/custom")
  public DataSource customDataSource(DataSourceProperties properties) {
    return DataSourceBuilder.create().type(HikariDataSource.class)
        .driverClassName(properties.getDriverClassName())
        .url(properties.getUrl())
        .username(properties.getUsername())
        .build();
  }
}
