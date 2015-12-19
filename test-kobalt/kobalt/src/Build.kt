import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.kotlin.*
import com.beust.kobalt.plugin.application.*

val repos = repos()

val p = kotlinProject {
    name = "test-kobalt"
    group = "com.example"
    artifactId = name
    version = "0.1"

    sourceDirectories {
        path("src/main/kotlin")
        path("src/main/resources")
        path("src/main/res")
    }

    sourceDirectoriesTest {
        path("src/test/resources")
        path("src/test/res")
        path("src/test/kotlin")
    }

    dependencies {
        //compile("com.beust:jcommander:1.48")
        //compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0-beta-3595")
    }

    dependenciesTest {
        //compile("org.testng:testng:6.9.5")
    }

    assemble {
        jar {
            fatJar = true
            manifest {
                attributes("Main-Class", "demo.MainKt")
            }
        }
    }

    application {
        mainClass = "demo.MainKt"
        jvmArgs("-Djava.library.path=libs", "-Ddebug=true")
    }
}
