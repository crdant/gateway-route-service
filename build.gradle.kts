import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `maven-publish`
	id("org.springframework.boot") version "2.2.5.RELEASE" apply false
	id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
	kotlin("jvm") version "1.3.61" apply false
	kotlin("plugin.spring") version "1.3.61" apply false
}

allprojects {
  repositories {
    jcenter() 
  }
}

subprojects {
  apply(plugin = "org.springframework.boot")
  apply(plugin = "io.spring.dependency-management")
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "org.jetbrains.kotlin.plugin.spring")
  apply(plugin = "maven-publish")

  group = "io.pivotal.pa.newengland.demo"
  version = "0.0.1-SNAPSHOT"

  configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
  }

  extra["springCloudVersion"] = "Hoxton.SR1"

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "11"
    }
  }

  dependencies {
    "implementation"("org.springframework.boot:spring-boot-starter-webflux")
    "implementation"("org.springframework.boot:spring-boot-starter-actuator")
    "implementation"("org.jetbrains.kotlin:kotlin-reflect")
    "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    
    "testImplementation"("org.springframework.boot:spring-boot-starter-test") {
      exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    "testImplementation"("io.projectreactor:reactor-test")
  }

}


