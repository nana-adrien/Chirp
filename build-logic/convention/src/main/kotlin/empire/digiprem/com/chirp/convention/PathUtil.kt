package empire.digiprem.com.chirp.convention

import org.gradle.api.Project
import java.util.Locale


fun Project.pathToPackageName(): String {
    val relativePackageName = path
        .replace(':', '.')
        .lowercase()
    return "empire.digiprem.com$relativePackageName"
}

fun Project.pathToResourcePrefix(): String {
    return path
        .replace(':', '_')
        .lowercase().drop(1) + "_"
}
fun Project.pathToFrameworkName(): String {
    val parts= path.split(":","_","-"," ")
    return parts.joinToString("") {parts->
            parts.replaceFirstChar {
                it.titlecase(Locale.ROOT)
            }
        }
}