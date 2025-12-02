package empire.digiprem.com.chirp.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope


// common main implementation

 fun DependencyHandlerScope.commonMainImplementation(project: Project) {
    "commonMainImplementation"(project)
}
 fun DependencyHandlerScope.commonMainImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    "commonMainImplementation"(dependency)
}
fun DependencyHandlerScope.commonMainImplementation(library: String) {
    "commonMainImplementation"(library)
}

// android main implementation

 fun DependencyHandlerScope.androidMainImplementation(project: Project) {
    "commonMainImplementation"(project)
}
fun DependencyHandlerScope.androidMainImplementation(library: String) {
    "commonMainImplementation"(library)
}
fun DependencyHandlerScope.androidMainImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    "commonMainImplementation"(dependency)
}