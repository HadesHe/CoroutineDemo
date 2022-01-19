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
        vectorDrawables {
            useSupportLibrary = true
        }
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
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Versions.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(Deps.AndroidX.navigationKtx)
    implementation(Deps.AndroidX.navigationUIKtx)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(project(mapOf("path" to ":webmodule")))
    implementation(Deps.Compose.ui)
    implementation(Deps.Compose.material)
    implementation(Deps.Compose.uiPreview)
    implementation(Deps.Compose.activityCompose)
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