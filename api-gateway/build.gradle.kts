import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("io.pivotal.spring.cloud:spring-cloud-sso-connector:2.1.3.RELEASE")

	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

