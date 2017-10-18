## Butter Knife

### Setup

Edit `build.gradle -> buildscript -> dependencies`, add `classpath`:

```groovy
buildscript {
    ...
      
    dependencies {
        ...

        classpath 'com.jakewharton:butterknife-gradle-plugin:8.5.1'
    }
}
```

Edit `build.gradle` , add plugin declaration:

```groovy
...
apply plugin: 'com.jakewharton.butterknife'
```

Edit `build.gradle -> dependencies`, add `compile` and `annotationProcessor`:

```groovy
dependencies {
	...

	compile 'com.jakewharton:butterknife:8.8.1'
	annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
}
```

