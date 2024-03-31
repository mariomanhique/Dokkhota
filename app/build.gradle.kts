plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.9.22"
}


    extra["versionMajor"] = 0
    extra["versionMinor"] = 0
    extra["versionPatch"] = 1
    extra["versionClassifier"] = null
    extra["isSnapshot"] = false
    extra["minSdkVersion"] = 29

    val versionMajor = extra["versionMajor"] as Int
    val versionMinor = extra["versionMinor"] as Int
    val versionPatch = extra["versionPatch"] as Int
    var versionClassifier = extra["versionClassifier"] as String?
    val isSnapshot = extra["isSnapshot"] as Boolean
    val minSdkVersion = extra["minSdkVersion"] as Int

    fun generateVersionCode(): Int {
        return minSdkVersion * 10000000 + versionMajor * 10000 +
                versionMinor * 100 + versionPatch
    }

    fun generateVersionName(): String {
        var versionName = "$versionMajor.$versionMinor.$versionPatch"

        if (versionClassifier == null) {
            if (isSnapshot) {
                versionClassifier = "SNAPSHOT"
            }
        }

        versionClassifier?.let {
            versionName += "-$versionClassifier"
        }

        return versionName
    }

android {
    namespace = "com.mariomanhique.dokkhota"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mariomanhique.dokkhota"
        minSdk = 29
        targetSdk = 34
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
//    implementation("androidx.compose.material3:material3-andr")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material-icons-extended")

//    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
//
//    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
//    implementation("com.google.firebase:firebase-analytics")
//
      implementation("com.google.dagger:hilt-android:2.49")
      ksp("com.google.dagger:hilt-compiler:2.48.1")
      implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
//
//    implementation("com.google.firebase:firebase-analytics")
//
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
//
//    // Pager - Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.27.0")
//
//    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")
//
//    //Google Auth
    implementation("com.google.android.gms:play-services-auth:21.0.0")
//
//    //Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")
//
//    //Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
//
//    //Desugar JDK
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
//
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
//
//    //Coroutine lifecycle scope
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
//
//    //Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.3")
//
//    // Date-Time Picker
//    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
//
//    // CALENDAR
//    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
//
//    // CLOCK
//    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")
//
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.tracing:tracing-ktx:1.2.0")
//
    implementation("androidx.datastore:datastore:1.0.0")
//    implementation("com.google.protobuf:protobuf-javalite:3.24.4")
//    implementation("com.google.protobuf:protobuf-kotlin-lite:3.24.4")
//    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
//    implementation("com.google.protobuf:protoc:3.24.4")
//
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0" )
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
//
//    implementation("com.cloudinary:cloudinary-android:2.5.0")//All cloudinary features

}