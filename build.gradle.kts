plugins {
	id("org.springframework.boot") version "2.2.5.RELEASE" apply false
	id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
  id("com.google.cloud.tools.jib") version "1.8.0" apply false
	kotlin("jvm") version "1.3.61" apply false
	kotlin("plugin.spring") version "1.3.61" apply false
}

allprojects {
  repositories {
    jcenter() 
  }
}

subprojects {
  group = "io.pivotal.pa.newengland.demo"
  version = "0.0.1-SNAPSHOT"
}


