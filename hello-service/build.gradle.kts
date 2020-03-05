import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
  
dependencies {
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.security:spring-security-oauth2-jose")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
  implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

  implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

