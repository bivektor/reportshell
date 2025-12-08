# Sample Reports

This directory contains the JasperReports source files (`.jrxml`) and related resources used by the
sample applications.

## Reports

- **Customers.jrxml**: A sample report listing customers. This report features:
    - Nested multi-valued cascading controls for regions, countries and cities
    - Default value evaluation for the `createdAfter` control
    - Format masking for dates and numbers
- **CustomerSpend.jrxml**: This report also lists customers but without any filters. It features:
    - Subreport resolution relative to a root directory
    - Viewer behavior for reports without input control parameters
- **Orders.jrxml**: Tabular order history report with filters. It features referencing custom
  data sources for the report data set and input control options.
  This report works only in the custom-data-source sample.
- **region/Countries.jrxml**: List of countries filtered by a region.
  Demonstrates report lookup by a nested path in URLs like `/view/region/countries`.

## Resources

- **shared**: Shared resources and subreports to demonstrate resolution of resources 
relative to the parent directory.
- **DemoDB.jrdax**: Data Adapter for the demo database to help testing reports in 
Jaspersoft Studio.

## Importing to Jaspersoft Studio

This folder is a Jaspersoft Studio project. Follow these steps to import it:

1. Open Jaspersoft Studio
2. Navigate to **File** → **Import**
3. Select **General** → **Existing Projects into Workspace**
4. Click **Next**
5. In the **Select root directory** field, browse and select the `samples` folder (the parent folder
   that contains this `reports` folder)
6. The **ReportShell Demo** project should appear in the **Projects** section
7. Ensure the project is checked
8. Click **Finish**

> **Important**  
> ReportShell recognizes compiled reports only. When you make any change to a report in the Studio,
> make sure you build the project before testing it in a sample application.
>

### Accessing the demo database in JasperSoft Studio

This folder contains the `DemoDB.jrdax` data adapter file which points to the sample H2 database
that
you can use to test reports in Jaspersoft Studio. You need a running H2 database server to use that
data adapter.

To run the database server, follow the instructions
in [Database Server README](../projects/db-server/README.md)

