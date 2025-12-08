# ReportShell Quick Start Sample

## Overview

This sample demonstrates the simplest setup for ReportShell using the default configuration. It
- uses the default report store and searches reports under the `samples/reports` folder.
- does not work for reports or controls that specify a custom data source such as the
  **orders** report.

## Running the Sample

> **Important**
> Before running this sample, make sure you've read the instructions on running the samples in the
> repository [README](../../README.md)

1. Start the application:
   From the `samples/projects` directory
   ```bash
   ./mvnw -pl maven-quickstart spring-boot:run
   ```

2. Open your browser to `http://localhost:8080`
