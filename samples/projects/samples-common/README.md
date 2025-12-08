# Samples Common

Shared library and resources used by all sample applications.

## Java Components

- **SamplesCommonConfiguration** - Spring configuration that registers shared beans
- **RestExceptionHandler** - Global exception handler for REST API errors

## Shared Resources

This module packages shared resources into its JAR so that all dependent sample applications
automatically have them on the classpath.

### Templates

- `templates/index.html` - Landing page listing all available sample reports with descriptions
- `templates/reportshell/viewer.html` - Report viewer page that renders individual reports

### Spring Configuration

- `base-config.yml` - Shared Spring Boot configuration imported by sample applications via
  `spring.config.import: "classpath:base-config.yml"`. Configures the embedded H2 demo database,
  SQL init scripts, H2 console, and the home page view controller.

### Database Init Scripts

- `db/schema.sql` - H2 database schema with cascading lookup tables and reporting views
- `db/data.sql` - Seed data for regions, countries, cities, products, customers, and orders

### Static Resources

- `META-INF/resources/reportshell-icon.png` - ReportShell logo icon (favicon)
- `META-INF/resources/reportshell-logo.png` - Logo shown in the page header

### JasperReports Configuration

- `jasperreports_extension.properties` - Registers the JasperReports file repository service and
  sets the report root to `../../reports` (relative to the application's working directory).

## Usage

All Gradle samples depend on this module via the root `build.gradle.kts`:

```kotlin
implementation(project(":samples-common"))
```

The Maven quickstart sample depends on the published artifact:

```xml
<dependency>
  <groupId>com.reportshell.samples</groupId>
  <artifactId>samples-common</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
