plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = (Deps.Versions.compileSdk)

    defaultConfig {
        applicationId = "com.huami.coroutinedemo"
        minSdk = (Deps.Versions.minSdk)
        targetSdk = (Deps.Versions.targetSdk)

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Deps.Versions.javaVersion
        targetCompatibility = Deps.Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Deps.Versions.javaVersion.toString()
    }

    android {
        buildFeatures {
            viewBinding = true
        }
    }
}

dependencies {

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.constraintlayout)
    implementation(Deps.AndroidX.workmanager)
    implementation(Deps.AndroidX.lifecycleViewModel)
    implementation(Deps.AndroidX.lifecycleRuntime)
    implementation(Deps.AndroidX.lifecycleLivedata)
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.AndroidTest.junit)
    androidTestImplementation(Deps.AndroidTest.espresso)

    implementation(Deps.Kotlinx.coroutineCore)
    implementation(Deps.Kotlinx.coroutineAndroid)

    implementation(Deps.Koin.koin)
    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.Web.okhttp)
    implementation(Deps.Web.okhttpInterceptor)

}