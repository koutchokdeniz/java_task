plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.12.4")
    implementation("mysql:mysql-connector-java:8.0.26")
    implementation("com.sparkjava:spark-core:2.9.3")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.slf4j:slf4j-log4j12:1.7.32")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("org.hibernate:hibernate-core:5.5.6.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.5.6.Final")
    implementation("javax.transaction:javax.transaction-api:1.3")
    implementation("com.h2database:h2:1.4.200")



}

tasks.test {
    useJUnitPlatform()
}