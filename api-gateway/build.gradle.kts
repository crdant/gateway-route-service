buildscript {
	ext {
		kotlinVersion = '1.2.71'
		springBootVersion = '2.1.1.RELEASE'
    jibVersion = '1.8.0'
	}
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
	}
	repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    classpath("com.google.cloud.tools.jib:com.google.cloud.tools.jib.gradle.plugin:${jibVersion}")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
		classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}")
	}
}

apply plugin: 'kotlin'
apply plugin: 'kotlin-spring'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'
apply plugin: 'com.google.cloud.tools.jib'

sourceCompatibility = 1.8
compileKotlin {
	kotlinOptions {
		freeCompilerArgs = ["-Xjsr305=strict"]
		jvmTarget = "1.8"
	}
}
compileTestKotlin {
	kotlinOptions {
		freeCompilerArgs = ["-Xjsr305=strict"]
		jvmTarget = "1.8"
	}
}

repositories {
	mavenCentral()
	maven { url "https://repo.spring.io/milestone" }
}

ext['springCloudVersion'] = 'Greenwich.RELEASE'
ext['springCloudServicesVersion'] = '2.1.0.RELEASE'

dependencies {
	implementation("io.pivotal.spring.cloud:spring-cloud-services-starter-config-client")
	implementation("io.pivotal.spring.cloud:spring-cloud-services-starter-service-registry")

	implementation('org.springframework.boot:spring-boot-starter-actuator')
	implementation('org.springframework.boot:spring-boot-starter-webflux')

	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	implementation 'io.pivotal.spring.cloud:spring-cloud-sso-connector:2.1.3.RELEASE'

	implementation('com.fasterxml.jackson.module:jackson-module-kotlin')
	implementation('org.springframework.cloud:spring-cloud-starter-gateway')
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	testImplementation('org.springframework.boot:spring-boot-starter-test')
	testImplementation('io.projectreactor:reactor-test')
}

dependencyManagement {
	imports {
		mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
		mavenBom "io.pivotal.spring.cloud:spring-cloud-services-dependencies:${springCloudServicesVersion}"
	}
}

jib {
  to.image = 'crdant/greeting-api-gateway'
}
