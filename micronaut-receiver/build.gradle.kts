import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.model.AllOpen
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("com.github.johnrengelman.shadow") version "4.0.2"
    id("application")
    kotlin("jvm") version "1.3.21"
    kotlin("kapt") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.31"
}

version = "0.1"
group = "micronaut.receiver"

repositories {
    mavenCentral()
    jcenter()
}

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:1.1.0")
    }
}

// THIS CONVERSION WAS NON OBVIOUS
//configurations {
//    // for dependencies that are needed for development only
//    developmentOnly
//}
// helpful -> https://github.com/spring-projects/spring-boot/issues/16251
val developmentOnly = configurations.create("developmentOnly")
configurations.runtimeClasspath.get().extendsFrom(developmentOnly)

dependencies {

    val kotlinVersion: String by project

    compile("io.micronaut.configuration:micronaut-kafka")
    compile("io.github.microutils:kotlin-logging:1.6.24")

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    compile("javax.annotation:javax.annotation-api")

    compile("io.micronaut:micronaut-runtime")
    compile("io.micronaut:micronaut-http-server-netty")
    compile("io.micronaut:micronaut-management:1.1.0")
    compile("io.micronaut:micronaut-http-client:1.1.0")
    compile("io.micronaut.configuration:micronaut-micrometer-core:1.1.0")
    compile("io.micronaut.configuration:micronaut-micrometer-registry-prometheus:1.1.0")

    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")

    kaptTest("io.micronaut:micronaut-inject-java")

    runtime("ch.qos.logback:logback-classic:1.2.3")
    runtime("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")

    testAnnotationProcessor("io.micronaut:micronaut-inject-java")

    testCompile("io.micronaut.test:micronaut-test-junit5")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

// https://docs.gradle.org/5.2/userguide/managing_dependency_configurations.html#defining_custom_configurations

// test.classpath += configurations.developmentOnly
configurations.testRuntimeClasspath.get().extendsFrom(developmentOnly)

application {
    mainClassName = "micronaut.receiver.Application"
}

// use JUnit 5 platform
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

// seems a dupe of earlier
//run.classpath += configurations.developmentOnly

// run.jvmArgs = "-noverify"; "-XX:TieredStopAtLevel=1"; "-Dcom.sun.management.jmxremote"
// https://guides.gradle.org/migrating-build-logic-from-groovy-to-kotlin/#configuring-tasks
tasks.named<JavaExec>("run"){
    doFirst {
        jvmArgs = listOf("-noverify",   "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
    }
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.javaParameters = true
}
// N.B. You dote need additional task for CompileTestKotlin, Test Kotlin inherits, I think?!
