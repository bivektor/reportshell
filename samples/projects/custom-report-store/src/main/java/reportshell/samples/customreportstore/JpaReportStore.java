package reportshell.samples.customreportstore;

import com.bivektor.reportshell.core.ReportKey;
import com.bivektor.reportshell.core.metadata.ReportDescriptor;
import com.bivektor.reportshell.core.metadata.store.ReportDescriptorStore;
import com.bivektor.reportshell.core.repository.RepositoryReportDescriptor;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperReportsContext;
import org.springframework.stereotype.Component;

/**
 * Custom report store implementation that retrieves report metadata from a database.
 */
@RequiredArgsConstructor
@Component
public class JpaReportStore implements ReportDescriptorStore {

  @NonNull
  private final ReportRepository reportRepository;

  @Override
  @NonNull
  public Optional<ReportDescriptor> getReportDescriptor(@NonNull ReportKey key, @NonNull JasperReportsContext context) {
    return findReportByKey(key).map(this::createDescriptor);
  }

  private ReportDescriptor createDescriptor(Report report) {
    return SimpleReportDescriptor.builder()
      .compiledReportLocation(report.getCompiledReportFile())
      .reportId(report.getId().toString())
      .technicalName("report" + report.getId()) // binding object name for parameter binding results
      .displayName(report.getName())
      .build();
  }

  private Optional<Report> findReportByKey(@NonNull ReportKey key) {
    if (key.getType().equals(ReportKey.TYPE_ID))
      return reportRepository.findById(Long.parseLong(key.getValue()));
    else
      return reportRepository.findByName(key.getValue());
  }

  /**
   * Report descriptors must be immutable. Never pass an entity to them.
   */
  @Builder
  @Getter
  private static class SimpleReportDescriptor implements RepositoryReportDescriptor {
    private final String compiledReportLocation;
    private final String reportId;
    private final String technicalName;
    private final String displayName;

    @Override
    public boolean isClientExecutable() {
      return true;
    }
  }
}
