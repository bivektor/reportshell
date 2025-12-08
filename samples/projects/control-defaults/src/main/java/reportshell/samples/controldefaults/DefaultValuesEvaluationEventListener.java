package reportshell.samples.controldefaults;

import com.bivektor.reportshell.core.input.ControlDefaultValueListener;
import com.bivektor.reportshell.core.input.ControlStatesLoaderContext;
import java.time.LocalDate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DefaultValuesEvaluationEventListener implements ControlDefaultValueListener {

  /**
   * Listener method triggered just before the parameter default value expressions are evaluated. This happens only when
   * initially loading the control states via the input-controls REST API.
   */
  @Override
  public void beforeEvaluate(
      @NonNull ControlStatesLoaderContext context
  ) {
    var reportName = context.getReportMetadata().getName();
    if (!"customers".equalsIgnoreCase(reportName)) {
      return;
    }

    // Default value expressions are evaluated only if the parameter value map doesn't contain a value.
    // Once we set the value here, the default value expression won't evaluate.
    // We use putIfAbsent in order not to override an existing value if the UI is
    // loading with initial parameters.
    context.getParameterValues()
        .putIfAbsent("createdAfter", java.sql.Date.valueOf(LocalDate.now().minusYears(2)));
  }
}
