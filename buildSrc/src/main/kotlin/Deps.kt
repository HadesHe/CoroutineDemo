import org.gradle.api.JavaVersion

/**
 * @date 2021/12/23
 * @author hezhanghe@zepp.com
 * @desc
 */
object Deps {

    object Versions {
        const val compileSdk = 31
        const val minSdk = 21
        const val targetSdk = compileSdk
        val javaVersion = JavaVersion.VERSION_11
        val composeVersion  = "1.0.5"
        const val kotlinVersion = "1.5.31"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val material = "com.google.android.material:material:1.4.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.2"
        const val workmanager = "androidx.work:work-runtime-ktx:2.7.1"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"
        const val webkit = "androidx.webkit:webkit:1.4.0"
        const val fragmentktx = "androidx.fragment:fragment-ktx:1.4.0"
        const val fragment = "androidx.fragment:fragment:1.4.0"

        const val navigationKtx = "androidx.navigation:navigation-fragment-ktx:2.3.5"
        const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:2.3.5"
        const val navigationSafeArg = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5"
        const val activityKtx = "androidx.activity:activity-ktx:1.4.0"
    }

    object Web {
        /**
         * tencent x5 由于其会下发 .so 被 play 禁用
         * 可以使用 crosswalk 替换
         */
        const val tbs = "com.tencent.tbs.tbssdk:sdk:43993"
        const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"
        const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
        const val okio = "com.squareup.okio:okio:2.10.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
    }

    object AndroidTest {
        const val junit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Kotlinx {
        const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
        const val coroutineCoreJvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.2"
        const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
    }

    object Koin {
        const val koin = "io.insert-koin:koin-core:3.1.4"
        const val koinAndroid = "io.insert-koin:koin-android:3.1.4"
    }

    object Compose{
        val ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
        val material = "androidx.compose.material:material:${Versions.composeVersion}"
        val uiPreview = "androidx.compose.ui:ui-tooling-preview:1.0.5"
        const val activityCompose = "androidx.activity:activity-compose:1.4.0"
    }
}