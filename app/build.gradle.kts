plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.upao.felihouse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.upao.felihouse"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.gridlayout)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation ("org.java-websocket:Java-WebSocket:1.5.2")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.airbnb.android:lottie:2.8.0")
    implementation ("com.google.firebase:firebase-database:19.3.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}