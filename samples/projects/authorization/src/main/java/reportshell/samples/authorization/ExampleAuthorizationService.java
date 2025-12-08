package reportshell.samples.authorization;

import com.bivektor.reportshell.core.CompiledReport;
import com.bivektor.reportshell.core.CompiledReportAccessor;
import com.bivektor.reportshell.security.ReportAuthorizationContext;
import com.bivektor.reportshell.security.ReportAuthorizationService;
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
public class ExampleAuthorizationService implements ReportAuthorizationService {

  @Override
  public boolean isAuthorized(@NotNull ReportAuthorizationContext context) {
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

    var compiledReport = tryResolveCompiledReport(context);
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
   * Check if we can resolve a compiled report from the context.
   * <p>
   * Ideally, this should not be needed because either the report descriptors contain necessary authorization like
   * permissions or roles, or a service/store loads them.
   * </p>
   * <p>
   * In this case, we trust that the system is using default conventions.
   * </p>
   */
  @Nullable
  private CompiledReport tryResolveCompiledReport(ReportAuthorizationContext context) {
    var report = context.getReport();

    // Default store returns a descriptor that already contains the compiled report
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
