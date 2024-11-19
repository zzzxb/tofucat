plugins {
    `java-library`
    `maven-publish`
}

group = "xyz.zzzxb.tofucat.protocol"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            uri(file("/opt/data/mvn_repo"))
        }
    }
}

dependencies {
    implementation(project(":tofucat-common"))
    implementation("io.netty:netty-all:4.1.113.Final")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-core:1.5.8")
    testImplementation("ch.qos.logback:logback-classic:1.5.8")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}