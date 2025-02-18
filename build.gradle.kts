plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kadaster"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-security:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.20")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.3.4")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.0.21")
	testImplementation("org.springframework.security:spring-security-test:6.3.3")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.2")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
