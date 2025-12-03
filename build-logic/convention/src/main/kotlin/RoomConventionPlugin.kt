import androidx.room.gradle.RoomExtension
import empire.digiprem.com.chirp.convention.commonMainApi
import empire.digiprem.com.chirp.convention.commonMainImplementation
import empire.digiprem.com.chirp.convention.ksp
import empire.digiprem.com.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.google.devtools.ksp")
                apply("androidx.room")

            }

           extensions.configure<RoomExtension>{
               schemaDirectory("$projectDir/shemas")
           }

            dependencies{
                commonMainApi(libs.findLibrary("androidx-room-runtime").get())
                commonMainApi(libs.findLibrary("sqlite-bundled").get())
                ksp(libs.findLibrary("androidx-room-compiler").get())
            }
        }
    }


}