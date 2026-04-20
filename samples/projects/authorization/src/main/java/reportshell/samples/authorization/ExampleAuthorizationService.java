package reportshell.samples.authorization;

import com.bivektor.reportshell.core.CompiledReport;
import com.bivektor.reportshell.core.CompiledReportAccessor;
import com.bivektor.reportshell.core.context.ReportOperationContext;
import com.bivektor.reportshell.security.AuthorizationContext;
import com.bivektor.reportshell.security.AuthorizationService;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRReport;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExampleAuthorizationService implements AuthorizationService {

  @Override
  public boolean isAuthorized(@NotNull AuthorizationContext context) {
    var auth = (Authentication) context.getUser();

    var userRoles = auth == null
      ? Collections.emptySet()
      : auth.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());

    if (userRoles.contains("ROLE_ADMIN")) {
      return true;
    }

    // Reject non-report operations (e.g., Executions API).
    // In DEVELOPER mode (no license), only report-bound operations are available, so this
    // check won't trigger here — but it's included for completeness.
    if (!(context.getOperationContext() instanceof ReportOperationContext reportContext)) {
      return false;
    }

    var compiledReport = tryResolveCompiledReport(reportContext);
    if (compiledReport == null) {
      return false;
    }

    var jasperReport = compiledReport.getJrReport();
    var allowedRoles = getAllowedRoles(jasperReport);
    if (allowedRoles.isEmpty()) {
      return true;
    }

    log.info("Allowed roles for report {}: {}", jasperReport.getName(), allowedRoles);
    return allowedRoles.stream().anyMatch(userRoles::contains);
  }

  /**
   * Resolves the compiled report from the operation context.
   * <p>
   * With the default file-based report store, the descriptor implements {@link CompiledReportAccessor}
   * because the report file itself is the first-class entity. This allows reading report properties
   * (such as {@code security.roles.allow}) directly from the compiled report.
   * </p>
   * <p>
   * Applications using a custom report store (e.g., database-backed) would typically load
   * authorization metadata from their own storage instead.
   * </p>
   */
  @Nullable
  private CompiledReport tryResolveCompiledReport(ReportOperationContext operationContext) {
    var report = operationContext.resolveReport();

    if (report instanceof CompiledReportAccessor compiledReportAccessor) {
      return compiledReportAccessor.getCompiledReport();
    }

    log.warn("Report {} didn't resolve a CompiledReport", report.getTechnicalName());
    return null;
  }

  private Set<String> getAllowedRoles(JRReport report) {
    var allowedRoles = report.getProperty("security.roles.allow");
    if (allowedRoles == null || allowedRoles.isBlank())
      return Collections.emptySet();

    return Arrays.stream(allowedRoles.split(","))
      .map(role -> "ROLE_" + role.trim().toUpperCase(Locale.ROOT))
      .collect(Collectors.toSet());
  }
}
