plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.pyrion.yaksok"

    // Flutter 플러그인 기본 값 대신 직접 최신 값 지정
    compileSdk = 36
    ndkVersion = "27.0.12077973"

    compileOptions {
        // Flutter 3.22 이상은 Java 17을 권장
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        applicationId = "com.pyrion.yaksok"
        // Firebase Auth 5.x 이상은 minSdk 23 이상 요구
        minSdk = 23
        targetSdk = 36
        versionCode = flutter.versionCode
        versionName = flutter.versionName
        multiDexEnabled = true // 필요 시 멀티덱스 활성화
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

flutter {
    source = "../.."
}
