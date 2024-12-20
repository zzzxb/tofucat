plugins {
    `java-library`
    `maven-publish`
}

group = "xyz.zzzxb.tofucat"
version = "1.2.4"

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
//            uri(file("/opt/data/mvn_repo"))
            val uname = System.getenv("GITHUB_ACTOR")
            val passwd = System.getenv("GITHUB_TOKEN")
            val repo = System.getenv("GITHUB_REPOSITORY")

            url = uri("/${uname}/${repo}")
            credentials {
                username = uname;
                password = passwd
            }
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")
}


tasks.test {
    useJUnitPlatform()
}