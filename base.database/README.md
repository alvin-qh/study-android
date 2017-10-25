## DBFlow

### Setup

Edit `build.gradle -> repositories`, add external maven repositories:

```groovy
allProjects {
    ...
    
    repositories {
        ...
    
        // required to find the project's artifacts
        maven { url "https://www.jitpack.io" }
    }
}
```

If use kotlin, add gradle plugin:

```groovy
apply plugin: 'kotlin-kapt' // required for kotlin.
```

Edit `build.grade -> dependencies`, add DBFlow dependency:

```groovy
dependencies {
    // if Java use this. If using Kotlin do NOT use this.
    annotationProcessor "com.github.Raizlabs.DBFlow:dbflow-processor:${dbflow_version}"
        
    // Use if Kotlin user.
    kapt "com.github.Raizlabs.DBFlow:dbflow-processor:${dbflow_version}"

    compile "com.github.Raizlabs.DBFlow:dbflow-core:${dbflow_version}"
    compile "com.github.Raizlabs.DBFlow:dbflow:${dbflow_version}"
    
    // kotlin extensions
    compile "com.github.Raizlabs.DBFlow:dbflow-kotlinextensions:${dbflow_version}"
    
    // sql-cipher database encryption (optional)
    compile "com.github.Raizlabs.DBFlow:dbflow-sqlcipher:${dbflow_version}"
    compile "net.zetetic:android-database-sqlcipher:${sqlcipher_version}@aar"
}
```