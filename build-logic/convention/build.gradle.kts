import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins{
    `kotlin-dsl`
}

group="empire.digiprem.com.conversion.buildlogic"

dependencies{
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

java{
    sourceCompatibility=JavaVersion.VERSION_17
    targetCompatibility=JavaVersion.VERSION_17
}

kotlin{
    compilerOptions{
        jvmTarget= JvmTarget.JVM_17
    }
}

tasks{
    validatePlugins{
        enableStricterValidation=true
        failOnWarning=true
    }
}

gradlePlugin{
    plugins{
        register("androidApplication"){
            id="empire.digiprem.com.android.application"
            implementationClass="AndroidApplicationConventionPlugin"
        }
        register("androidComposeApplication"){
            id="empire.digiprem.com.android.application.compose"
            implementationClass="AndroidApplicationComposeConventionPlugin"
        }
        register("cmpApplication"){
            id="empire.digiprem.com.cmp.application"
            implementationClass="CmpApplicationConventionPlugin"
        }

        register("kmpLibrary"){
            id="empire.digiprem.com.kmp.library"
            implementationClass="KmpLibraryConventionPlugin"
        }
    }
}