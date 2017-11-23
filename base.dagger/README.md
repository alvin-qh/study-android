# Dagger 使用指南



## I 安装 Dagger 依赖

### 1.1 安装基本依赖库

```groovy
dependencies {
    ...
    
    implementation "com.google.dagger:dagger:${dagger_version}"
    annotationProcessor "com.google.dagger:dagger-compiler:${dagger_version}"
}
```

基本依赖库即可以支持所有的 DI 操作

### 1.2 安装 Android 支持依赖 

```groovy
dependencies {
    ...
    
    implementation "com.google.dagger:dagger-android:${dagger_version}"
    implementation "com.google.dagger:dagger-android-support:${dagger_version}"
    annotationProcessor "com.google.dagger:dagger-android-processor:${dagger_version}"
}
```

Android 依赖库专门针对 `Acitivity`, `Fragment`等 Android 组件提供支持，代码更为简洁



## II 使用指南

* [基本应用指南](src/main/java/alvin/base/dagger/basic/README.md)
