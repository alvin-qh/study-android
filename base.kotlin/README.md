## Use Kotlin

### Setup 

Edit `build.grade -> buildscript -> dependencies`, add kotlin classpath:

```groovy
buildscript {
    ...

    dependencies {
        ...
        
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.51"    // Kotlin support
    }
}
```

Edit `build.gradle`, add plugin usage:

```groovy
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'
```

Edit `build.gradle -> dependencies`, add implementation:

```groovy
dependencies {
    ...
    
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jre7:1.1.51"
}
```

### Annotation Processing

See [Document](https://kotlinlang.org/docs/tutorials/android-frameworks.html)

#### ButterKnife

Add `kotlin-kapt` plugin and `butterknife-compiler` kapt dependency:

```groovy
apply plugin: 'kotlin-kapt'

dependencies {
    ...
    
    compile "com.jakewharton:butterknife:$butterknife-version"
    
    kapt "com.jakewharton:butterknife-compiler:$butterknife-version"
}
```

Bind view like this:

```kotlin
@BindView(R2.id.title)
lateinit var title: TextView
```

Bind event like this:

```kotlin
@OnClick(R2.id.hello)
internal fun sayHello() {
    Toast.makeText(this, "Hello, views!", LENGTH_SHORT).show()
}
```

#### DBFlow

Add `kotlin-kapt` plugin and `dbflow-processor` kapt dependency:

```groovy
apply plugin: 'kotlin-kapt'

dependencies {
    implementation "com.github.raizlabs.dbflow:dbflow-core:$dbflow_version"
    implementation "com.github.raizlabs.dbflow:dbflow:$dbflow_version"
    
    kapt "com.github.raizlabs.dbflow:dbflow-processor:$dbflow_version"
}
```

Define entity like this:

```kotlin
@Table(name="users", database = AppDatabase::class)
class User : BaseModel() {

    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id: Long = 0

    @Column
    var name: String? = null
}
```

#### Dagger

Add `kotlin-kapt` plugin and `dagger-compiler` kapt dependency:

```groovy
apply plugin: 'kotlin-kapt'

dependencies {
    ...
    
    kapt "com.google.dagger:dagger-compiler:$dagger-version"
}
```

Inject by constructor:

```kotlin
class Thermosiphon 
@Inject constructor(
        private val heater: Heater
) : Pump {
    // ...
}
```

Define module like this:

```kotlin
@Module
abstract class PumpModule {
    @Binds
    abstract fun providePump(pump: Thermosiphon): Pump
}

@Module(includes = arrayOf(PumpModule::class))
class DripCoffeeModule {
    @Provides @Singleton
    fun provideHeater(): Heater = ElectricHeater()
}
```

Define compontents like this:

```kotlin
@Singleton
@Component(modules = arrayOf(DripCoffeeModule::class))
interface CoffeeShop {
    fun maker(): CoffeeMaker
}
```