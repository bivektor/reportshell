# ReportShell Custom Report Data Source Example

## Overview

This sample demonstrates how to use a custom data source for your reports and input controls,
for example, when you need to pull data from a specific JDBC source or a custom provider.

## Running the Sample

> **Important**
> Before running this sample, make sure you've read the instructions on running the samples in the
> samples [README](/../README.md)

1. Start the application:
   From the `samples/projects` directory
   ```bash
   ./gradlew :custom-data-source:bootRun
   ```

2. Open your browser to `http://localhost:8080`.
3. Navigate to the **Orders** report.

This report specifies the data source path as 'dataSources/custom'. To check that, open the
`samples/reports/orders.jrxml` in a text editor and search for 'dataSources/custom'.

You can also open the `reports` project in Jaspersoft(R) Studio application, open the **orders** report
in **design mode**, navigate to `Advanced > Misc > Edit Properties` in the properties pane on the
right of the screen.

## Data Source Configuration

`ReportShellConfig` registers a custom `javax.sql.DataSource` bean with the name used in the
  report (`dataSources/custom`).

This is just to illustrate that the default data source resolution logic is based on a bean
named with the data source path in your report.

See https://reportshell.com/docs/concepts/data-sources for more details on how data sources
are resolved and how you can customize that.
