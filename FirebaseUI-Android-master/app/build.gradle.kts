// NOTE: this project uses Gradle Kotlin DSL. More common build.gradle instructions can be found in
// the main README.
plugins {
  id("com.android.application")
}

android {
    compileSdkVersion(Config.SdkVersions.compile)

    defaultConfig {
        minSdkVersion(Config.SdkVersions.min)
        targetSdkVersion(Config.SdkVersions.target)

        versionName = Config.version
        versionCode = 1

        resourcePrefix("fui_")
        vectorDrawables.useSupportLibrary = true
    }

    defaultConfig {
        multiDexEnabled = true
    }

    buildTypes {
        named("release").configure {
            // For the purposes of the sample, allow testing of a proguarded release build
            // using the debug key
            signingConfig = signingConfigs["debug"]

            postprocessing {
                isRemoveUnusedCode = true
                isRemoveUnusedResources = true
                isObfuscate = true
                isOptimizeCode = true
            }
        }
    }

    lintOptions {
        // Common lint options across all modules
        disable(
            "IconExpectedSize",
            "InvalidPackage", // Firestore uses GRPC which makes lint mad
            "NewerVersionAvailable", "GradleDependency", // For reproducible builds
            "SelectableText", "SyntheticAccessor" // We almost never care about this
        )

        // Module-specific
        disable("ResourceName", "MissingTranslation", "DuplicateStrings")

        isCheckAllWarnings = true
        isWarningsAsErrors = true
        isAbortOnError = true

        baselineFile = file("$rootDir/library/quality/lint-baseline.xml")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Config.Libs.Androidx.design)
    implementation(Config.Libs.Androidx.multidex)

    implementation(project(":auth"))
    implementation(project(":firestore"))
    implementation(project(":database"))
    implementation(project(":storage"))

    implementation(Config.Libs.Provider.facebook)
    // Needed to override Facebook
    implementation(Config.Libs.Androidx.cardView)
    implementation(Config.Libs.Androidx.customTabs)

    implementation(Config.Libs.Misc.glide)
    implementation("com.google.firebase:firebase-auth:21.0.1")
    annotationProcessor(Config.Libs.Misc.glideCompiler)

    // Used for FirestorePagingActivity
    implementation(Config.Libs.Androidx.paging)

    // The following dependencies are not required to use the Firebase UI library.
    // They are used to make some aspects of the demo app implementation simpler for
    // demonstrative purposes, and you may find them useful in your own apps; YMMV.
    implementation(Config.Libs.Misc.permissions)
    implementation(Config.Libs.Androidx.constraint)
    debugImplementation(Config.Libs.Misc.leakCanary)
    debugImplementation(Config.Libs.Misc.leakCanaryFragments)
    releaseImplementation(Config.Libs.Misc.leakCanaryNoop)
    testImplementation(Config.Libs.Misc.leakCanaryNoop)
}

apply(plugin = "com.google.gms.google-services")
