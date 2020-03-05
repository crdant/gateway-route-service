import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
	implementation("org.springframework.cloud:spring-cloud-config-server")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

