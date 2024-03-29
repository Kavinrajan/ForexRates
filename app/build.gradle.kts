import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // id("kotlin-kapt")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

//val projectProperties = readProperties(file("dynamic_config.properties"))
val apiKey: String = gradleLocalProperties(rootDir).getProperty("api_key")
val baseUrl: String = gradleLocalProperties(rootDir).getProperty("base_url")

android {
    namespace = "com.kavin.forex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kavin.forex"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
            buildConfigField("String", "apiKey", "\"$apiKey\"")
            buildConfigField("String", "baseUrl", "\"$baseUrl\"")
        }
        debug {
            buildConfigField("String", "apiKey", "\"$apiKey\"")
            buildConfigField("String", "baseUrl", "\"$baseUrl\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
          targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
      //  compose = true
        viewBinding = true
        buildConfig = true
    }
   /* composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }*/
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // view
    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase
/*    implementation ("com.google.firebase:firebase-messaging-ktx:23.2.1")
    implementation ("com.google.firebase:firebase-bom:32.2.0")
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.4.0")
    implementation ("com.google.firebase:firebase-firestore-ktx:24.7.0")
    implementation ("com.google.firebase:firebase-auth-ktx:22.1.1")*/
    implementation ("com.google.android.material:material:1.11.0")

    // Testing
    implementation ("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("androidx.test:core:1.5.0")
    testImplementation ("org.mockito:mockito-core:5.3.1")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation ("io.mockk:mockk:1.13.5")
    testImplementation ("com.google.truth:truth:1.1.5")
    testImplementation ("app.cash.turbine:turbine:1.0.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    implementation("com.squareup.okio:okio:3.5.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation ("androidx.arch.core:core-testing:2.2.0")

    // WorkManager
    implementation ("androidx.work:work-runtime-ktx:2.8.1")
    implementation ("androidx.work:work-gcm:2.8.1")

    // Navigation Component
    val nav_version = "2.6.0"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Lifecycle components
    val lifecycle_version = "2.6.1"

    // viewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
/*    // viewModel utilities for Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")*/
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without viewModel or LiveData)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // Retrofit
    //networking
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Retrofit with Moshi Converter
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    // Moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-compiler:2.46.1")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.46.1")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    // Room
    val room_version = "2.5.2"

    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")

    // JWT
    implementation ("io.github.nefilim.kjwt:kjwt-core:0.8.0")
    implementation ("io.github.nefilim.kjwt:kjwt-jwks:0.8.0")
    implementation ("io.arrow-kt:arrow-core:1.0.1")

    // SSP-SDP library
    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
}

kapt {
    correctErrorTypes = true
}