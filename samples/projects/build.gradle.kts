plugins {
  id("org.springframework.boot") apply false
  id("io.freefair.lombok") apply false
  id("idea")
}

idea {
  module {
    excludeDirs.add(file("maven-quickstart"))
  }
}

subprojects {
  repositories {
    maven {
      url = uri("https://maven.pkg.github.com/bivektor/reportshell")
      content {
        includeGroup("com.bivektor.reportshell")
      }
      // Your GitHub Credentials
      // Either pass these properties or set up in your ~/.gradle/gradle.properties
      credentials {
        username = findProperty("github.user")?.toString() ?: System.getenv("GITHUB_ACTOR")
        password = findProperty("github.token")?.toString() ?: System.getenv("GITHUB_TOKEN")
      }
    }
    mavenCentral()
  }

  apply(plugin = "java")
  if (project.name != "samples-common") {
    apply(plugin = "org.springframework.boot")
  }
  apply(plugin = "io.spring.dependency-management")
  apply(plugin = "io.freefair.lombok")

  the<JavaPluginExtension>().apply {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  if (project.name != "samples-common") {
    val implementation by configurations
    val runtimeOnly by configurations

    dependencies {
      val reportShellVersion by properties
      val jasperReportsVersion by properties

      implementation("org.springframework.boot:spring-boot-starter-web")
      implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
      implementation("org.springframework.boot:spring-boot-starter-jdbc")
      implementation("com.bivektor.reportshell:reportshell-spring-boot-starter-web:$reportShellVersion")
      implementation(project(":samples-common"))

      runtimeOnly("net.sf.jasperreports:jasperreports-pdf:$jasperReportsVersion")
      runtimeOnly("net.sf.jasperreports:jasperreports-charts:${jasperReportsVersion}")
      runtimeOnly("net.sf.jasperreports:jasperreports-functions:${jasperReportsVersion}")
      runtimeOnly("net.sf.jasperreports:jasperreports-jdt:${jasperReportsVersion}")
      runtimeOnly("com.h2database:h2")
    }
  }

}