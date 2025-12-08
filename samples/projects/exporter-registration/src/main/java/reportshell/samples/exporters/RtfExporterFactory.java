package reportshell.samples.exporters;

import com.bivektor.reportshell.core.export.JasperExporterFactory;
import com.bivektor.reportshell.core.export.ReportExportContext;
import lombok.NonNull;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.export.RtfReportConfiguration;
import net.sf.jasperreports.export.SimpleRtfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RtfExporterFactory extends JasperExporterFactory<RtfReportConfiguration, JRRtfExporter> {

  @NonNull
  @Override
  protected JRRtfExporter createJasperExporter(@NonNull ReportExportContext context) {
    var exporter = new JRRtfExporter(context.getJasperContext());
    exporter.setExporterInput(context.getInput());
    exporter.setExporterOutput(new SimpleWriterExporterOutput(
        context.getOutputStream(),
        context.getDefaultCharacterEncoding()
    ));
    return exporter;
  }

  @NotNull
  @Override
  protected String getContentType(@NotNull JRRtfExporter jasperExporter, @NotNull ReportExportContext context) {
    return "application/rtf; charset=" + context.getDefaultCharacterEncoding();
  }

  @Nullable
  @Override
  protected RtfReportConfiguration createReportConfiguration(@NotNull ReportExportContext context) {
    return new SimpleRtfReportConfiguration();
  }
}
