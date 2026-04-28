package reportshell.samples.exporters;

import com.bivektor.reportshell.core.export.AbstractReportExporterFactory;
import com.bivektor.reportshell.core.export.ReportExportContext;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.export.RtfReportConfiguration;
import net.sf.jasperreports.export.SimpleRtfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RtfExporterFactory extends AbstractReportExporterFactory<RtfReportConfiguration, JRRtfExporter> {

  @NotNull
  @Override
  protected JRRtfExporter createJasperReportsExporter(@NotNull ReportExportContext context) {
    var exporter = new JRRtfExporter(context.getJasperReportsContext());
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
