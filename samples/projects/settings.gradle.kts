rootProject.name = "reportshell-samples"

pluginManagement {
  val springBootVersion: String by settings
  val lombokVersion: String by settings

  repositories {
    gradlePluginPortal()
    mavenCentral()
  }

  plugins {
    id("org.springframework.boot") version springBootVersion
    id("io.freefair.lombok") version lombokVersion
  }
}

include("samples-common")
include("quickstart")
include("db-server")
include("custom-data-source")
include("control-defaults")
include("custom-report-store")
include("exporter-registration")
include("authorization")