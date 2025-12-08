package reportshell.samples.customreportstore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reports")
public class Report {
  @Id @GeneratedValue
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String compiledReportFile;
}
