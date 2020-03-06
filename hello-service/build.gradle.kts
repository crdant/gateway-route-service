import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
 
plugins {
	id("org.springframework.boot") version "2.2.5.RELEASE" 
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

repositories {
  jcenter() 
}

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

dependencies {
  "implementation"("org.springframework.boot:spring-boot-starter-oauth2-client")
	"implementation"("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	"implementation"("org.springframework.security:spring-security-oauth2-jose")
	"implementation"("org.springframework.boot:spring-boot-starter-security")

	"implementation"("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
  "implementation"("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

  "implementation"("org.springframework.cloud:spring-cloud-starter-config")
	"implementation"("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	"testImplementation"("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

