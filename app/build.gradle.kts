plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tip102group01friendzy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tip102group01friendzy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}


dependencies {
    implementation (libs.androidx.navigation.compose.v284)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.runtime:runtime:1.5.1")

    //Below r for Gmap integration
    implementation (libs.maps.compose)
    implementation (libs.android.maps.utils)
    implementation (libs.play.services.maps)
    implementation (libs.gms.play.services.location) //(取得map位置資訊)
    implementation (libs.google.accompanist.permissions) //(簡化user定位授權過程
    implementation (libs.gms.play.services.maps)
    implementation (libs.maps.compose.v2110)



    implementation(libs.androidx.datastore.core.android)
    implementation(libs.play.services.nearby)
    implementation(libs.gson)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.location)
    implementation(libs.accompanist.permissions)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.analytics.ktx)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.maps.android:maps-compose:2.14.0")
    implementation ("androidx.compose.ui:ui-tooling:1.5.0")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("androidx.webkit:webkit:1.5.0")  // WebView 支援
    implementation("io.coil-kt:coil-compose:2.4.0") // Coil 2.x 版本 支持從網路、文件、資源等多種來源加載圖片
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.maps.android:android-maps-utils:2.3.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.0")

    implementation (libs.ui.tooling)
    implementation (libs.material3)
    implementation (libs.androidx.webkit)  // WebView 支援
    implementation(libs.coil.compose) // Coil 2.x 版本 支持從網路、文件、資源等多種來源加載圖片
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging.ktx)



    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}