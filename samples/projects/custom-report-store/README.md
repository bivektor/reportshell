## Custom Report Store

This sample demonstrates how to resolve reports from a RDMS using Spring JPA repositories. 

- We define a `Report` entity to hold report metadata such as the technical name and path to the compiled `.jasper` file.
- We register a `ReportRepository` that extends from Spring's `JpaRepository` to query `Report` entities by their keys
- We register a `JpaReportStore` using that repository

### Reports Table
See database resources under `samples-common` project resources on how the `reports`
database table is initialized. 

### Report File Resolution

This sample still uses the `.jasper` file resolution logic used in many other samples. Database only 
stores metadata while actual resolution to the report file is through JasperReports Library's 
repository and persistence services.

Reports are looked up in the database by the registration URL names and the
corresponding compiled JasperReport file location. 

### Subreport & Resource Resolution

This sample doesn't store subreports in the database. Subreports are still in the file system and resolved
relative to the JasperReports Library's repository contexts.

Storing subreports and/or static resources in a database requires more complex logic with custom 
repository and persistence services which is beyond this sample's scope.

## Running the Sample

> **Important**
> Before running this sample, make sure you've read the instructions on running the samples in the
> samples [README](../README.md)

1. Start the application:
   From the `samples/projects` directory
   ```bash
   ./gradlew :custom-report-store:bootRun
   ```

2. Open your browser to `http://localhost:8080`
3. Open any report page
4. Verify that reports are resolved correctly based on your database records and your report files.
