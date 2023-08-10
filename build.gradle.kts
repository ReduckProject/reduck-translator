plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.9.0"
}

group = "net.reduck"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
        options.encoding="UTF-8"
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("231.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    dependencies {
        implementation("com.squareup.okhttp3:okhttp:4.11.0")
        compileOnly("org.projectlombok:lombok:1.18.28")
        implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
        implementation("org.slf4j:slf4j-api:2.0.7")
    }
}
