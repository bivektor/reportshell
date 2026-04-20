# ReportShell Authorization Sample

This sample demonstrates how to implement role-based authorization for 
ReportShell reports using Spring Security.

## Overview

The authorization sample shows how to:

- Implement a custom `AuthorizationService` using `AuthorizationContext`
- Configure Spring Security with in-memory users and roles
- Store role-based permissions in a JRXML report property
- Control report viewing and execution based on user roles

## Architecture

### ExampleAuthorizationService

`ExampleAuthorizationService` implements `AuthorizationService` and checks permissions based on:

1. The authenticated user’s roles (from Spring Security)
2. Allowed roles defined in the report’s JRXML properties

In this sample, the authorization service casts the `ReportDescriptor` to `CompiledReportAccessor` 
to access the underlying `JasperReport`. This works because `DefaultReportStore` returns a 
`ReportDescriptor` that includes the `JasperReport` instance.

With a custom report store, you may return descriptors from a database or file system and 
load the `JasperReport` separately. In that case, the `ReportDescriptor` would not 
contain the `JasperReport` object, and you'd typically load permission mappings from
your own storage.

**Permission check logic:**

- Reads allowed roles from the report property `security.roles.allow`
- If no roles are defined, access is granted (permit-all)
- If roles are defined, access is granted only if the user has at least one required role

### Security Configuration

The `SecurityConfig` class configures Spring Security with:

- **Three demo users:**
    - `admin` / `admin` — Roles: ADMIN, USER
    - `manager` / `manager` — Roles: MANAGER, USER
    - `user` / `user` — Roles: USER
- Form-based authentication
- Public access to static resources

CSRF is enabled and the `viewer.html` in `samples-common` project makes sure it passes it
to the React viewer.

## Adding Authorization to Reports

To add role-based authorization to JRXML reports, define properties like:


```xml

<property name="security.roles.allow" value="MANAGER,USER"/>
```

This configuration allows:

- Only users with `ROLE_MANAGER` or `ROLE_USER` to access the viewer page, run and render the report.

## Running the Sample

> **Important**
> Before running this sample, make sure you've read the instructions on running the samples in the
> repository [README](../../README.md)

1. Start the application:
   From the `samples/projects` directory 
   ```bash
   ./gradlew :authorization:bootRun
   ```

2. Open your browser to `http://localhost:8080`

3. Log in with one of the demo accounts:
    - Username: `admin`, Password: `admin` (full access)
    - Username: `manager`, Password: `manager` (manager access)
    - Username: `user`, Password: `user` (basic access)

## Sample Reports

- **Customers** – Allows MANAGER access only
- **CustomerSpend** - Allows ADMIN access only

Other reports don't specify any roles in which case `ExampleAuthorizationService` assumes permit-all.
The service skips role check if the user has `ROLE_ADMIN` role. 

## Key Components

- **ExampleAuthorizationService.java** - Implements role-based authorization logic
- **SecurityConfig.java** - Configures Spring Security with in-memory users
- **AuthorizationApplication.java** - Spring Boot application entry point
