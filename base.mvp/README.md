# Dagger with android



## Setup dagger dependencies



### Primary dependency libraries

```groovy
dependencies {
    ...
    
    implementation "com.google.dagger:dagger:${dagger_version}"
    annotationProcessor "com.google.dagger:dagger-compiler:${dagger_version}"
}
```



### Android support libraries 

```groovy
dependencies {
    ...
    
    implementation "com.google.dagger:dagger-android:${dagger_version}"
    implementation "com.google.dagger:dagger-android-support:${dagger_version}"
    annotationProcessor "com.google.dagger:dagger-android-processor:${dagger_version}"
}
```



## Guide

* [Basic](src/main/java/alvin/base/mvp/basic/README.md)
