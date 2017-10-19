## Java 8 supported in Android

### Add retrolambda plugin dependenciey

Edit `build.gradle -> buildscript -> dependencies`, add `classpath` option:

```groovy
buildscript {
    ...
    
    dependencies {
        classpath 'me.tatarka:gradle-retrolambda:3.7.0'
        ...
    }
}
```



### Use retrolambda plugin 

Edit `build.gradle`, add apply plugin:

```groovy
...

apply plugin: 'me.tatarka.retrolambda'
```



## Set compile options for android

Edit `build.gradle -> android`, add `compileOptions` option:

```groovy
android {
    ...
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

