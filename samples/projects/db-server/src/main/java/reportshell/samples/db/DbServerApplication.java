package reportshell.samples.db;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbServerApplication {
  public static void main(String[] args) {
		SpringApplication.run(DbServerApplication.class, args);
  }

  /**
   * For demo purpose. It exposes embedded h2 db for the sample projects. It also allows JasperSoft Studio
   * to access the database and reeports inside the studio.
   */
  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server h2TcpServer() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9192");
  }
}
