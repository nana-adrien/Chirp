import com.android.build.gradle.LibraryExtension
import empire.digiprem.com.chirp.convention.configureKotlinAndroid
import empire.digiprem.com.chirp.convention.configureKotlinMultiplatform
import empire.digiprem.com.chirp.convention.libs
import empire.digiprem.com.chirp.convention.pathToResourcePrefix
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            configureKotlinMultiplatform()
            extensions.configure<LibraryExtension>{
                configureKotlinAndroid(this)
                resourcePrefix=this@with.pathToResourcePrefix()

                // Required to make build of app run in ios simulator
                experimentalProperties["android.experimental.kmp.enableAndroidResources"]="true"

            }
            dependencies{
                "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
                "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
            }
        }

    }


}