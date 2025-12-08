# ReportShell Exporter Registration Example

## Overview 

This sample demonstrates enabling additional export formats by registering a `ReportExporterFactory`.
ReportShell enables common export formats such as **PDF**, **CSV**, **Excel**, and **HTML** by default.
We add **Rich Text Format(RTF)** to the supported export formats in this example.

## Running the Sample

> **Important**
> Before running this sample, make sure you've read the instructions on running the samples in the
> samples [README](../README.md)

1. Start the application:
   From the `samples/projects` directory
   ```bash
   ./gradlew :exporter-registration:bootRun
   ```

2. Open your browser to `http://localhost:8080`
3. Open any report page
4. Run the report, and the **Export** menu shows the **RTF** as an option.

## Key Components
- `RtfExporterFactory`: Implementation of `ReportExporterFactory` to create RTF exporter instances
- `ReportShellConfig`: Configuration class to register the RTF exporter factory

## Configuration
- `application.yml`: Defines export format metadata for the `RTF` format that affects the display
  of the format option in the viewer's export menu.

