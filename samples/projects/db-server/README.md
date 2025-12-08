## Demo Database Server

This Spring Boot application starts an H2 Database server exposing it at port 9292.

You can use this database server for testing reports in Jaspersoft Studio as described in the
[Sample Reports README](../../reports/README.md)

From the `samples/projects` folder, run:

```
./gradlew :db-server:bootRun
```

Demo database is initialized using the schema and data files in the
[samples-common](../samples-common/README.md) module (`db/schema.sql` and `db/data.sql`) at startup.

**Note**: This application uses an in-memory database that is recreated when the application restarts.

