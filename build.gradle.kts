plugins {
    id("java")
    id("com.gradleup.shadow") version ("8.3.0")
}

group = "me.ghosty"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://mvn.intelligence-modding.de/haoshoku")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    compileOnly("xyz/haoshoku/nick:NickAPI:v6.7")

    /*shadowImpl("net.kyori:adventure-api:4.17.0")
    shadowImpl("net.kyori:adventure-text-minimessage:4.17.0")
    shadowImpl("net.kyori:adventure-text-serializer-legacy:4.17.0")
    shadowImpl("net.kyori:adventure-platform-bungeecord:4.3.3")*/

    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")
    implementation("net.kyori:adventure-platform-bungeecord:4.3.3")

    /*implementation("net.md-5:bungeecord-chat:1.20-R0.2") {
        isTransitive = false
    }*/

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

/*val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}*/

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    assemble {
        dependsOn(shadowJar)
    }

    /*shadowJar {
        configurations = listOf(shadowImpl)
    }*/
}