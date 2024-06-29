plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    val kotlinVersion = "1.8.0" // Kotlin 버전을 직접 정의
    repositories {
        google()
        mavenCentral() // Maven Central 저장소 추가
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3") // 버전을 2.5.3으로 업데이트
    }
}