plugins {
    alias(libs.plugins.convention.kmp.labrary)
 alias(libs.plugins.convention.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(projects.core.domain)
                // Add KMP dependencies here
                implementation(libs.bundles.ktor.common)
                implementation(libs.touchlab.kermit)
                implementation(libs.koin.core)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        nativeMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }

}