plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = (Deps.Versions.compileSdk)

    defaultConfig {
        minSdk = (Deps.Versions.minSdk)
        targetSdk = (Deps.Versions.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(Deps.Web.tbs)
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.AndroidTest.junit)
    androidTestImplementation(Deps.AndroidTest.espresso)
}