import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
  id("com.google.cloud.tools.jib")
	kotlin("jvm")
	kotlin("plugin.spring")
}

java.sourceCompatibility = JavaVersion.VERSION_11

extra["springCloudVersion"] = "Hoxton.SR1"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-config-server")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

jib {
  to.image = "crdant/configserver"
}
