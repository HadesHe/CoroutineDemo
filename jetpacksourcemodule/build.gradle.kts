plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = (Deps.Versions.compileSdk)

    defaultConfig {
        minSdk = (Deps.Versions.minSdk)
        targetSdk = (Deps.Versions.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Deps.Versions.javaVersion
        targetCompatibility = Deps.Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Deps.Versions.javaVersion.toString()
    }
}

dependencies {

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    testImplementation(Deps.Test.junit)

    implementation(Deps.AndroidX.workerManagerKtx)
}