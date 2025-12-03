import empire.digiprem.com.chirp.convention.configureAndroidCompose
import empire.digiprem.com.chirp.convention.configureAndroidTarget
import empire.digiprem.com.chirp.convention.configureIosTargets
import empire.digiprem.com.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("empire.digiprem.com.android.application.compose")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            configureAndroidTarget()
            configureIosTargets()
            dependencies{
                "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
            }
        }
    }
}