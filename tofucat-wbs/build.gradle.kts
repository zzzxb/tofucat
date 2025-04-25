plugins {
    id("java")
}

group = "xyz.zzzxb.tofucat"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.5")

    implementation(project(":tofucat-common"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.5")
}

tasks.test {
    useJUnitPlatform()
}