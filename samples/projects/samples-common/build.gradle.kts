import org.springframework.boot.gradle.plugin.SpringBootPlugin

dependencyManagement {
  imports {
    mavenBom(SpringBootPlugin.BOM_COORDINATES)
  }
}

dependencies {
  val reportShellVersion: String by project
  implementation("org.springframework:spring-webmvc")
  implementation("com.bivektor.reportshell:reportshell-web:${reportShellVersion}")
}