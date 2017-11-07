
# Signature For Apk



## Create a keystore file

### Create keystore

```sh
$ keytool -genkeypair -v -keyalg DSA -keysize 1024 -sigalg SHA1withDSA -validity 20000 -keystore .keystore/study-android.keystore -alias study-android -keypass 12345 -storepass 12345
```
- `-genkeypair`: Create key pair
- `-keyalg`: Encryption algorithm
- `-keysize`: Length of security key
- `-sigalg`: Signature hash algorithm
- `-validity`: Period of validity
- `-keystore`: Path and file name of keystore file
- `-alias`: Name of keystore file
- `-keypass`: Password of security key
- `-storepass`: Password of keystore file


### Check keystore

```sh
$ keytool -list -keystore .keystore/study-android.keystore
```



## Sign the apk

Use .keystore file:

```sh
$ jarsigner -verbose -sigalg SHA1withDSA -digestalg SHA1 -keystore .keystore/study-android.keystore -storepass 12345 build/output/test.apk study-android
```

Use .pk8 and .x509.pem:

```sh
$ java -jar signapk.jar key.x509.pem key.pk8 test.apk test.signed.apk
```

Key file format can convert between `.keystore` and `.pk8 + .x509.epm` 



## Config build.gradle

Edit `build.gradle -> android -> signingConfigs / buildTypes`, add keystore config: 

```groovy
android {
    ...
    
    signingConfigs {
        release {
            keyAlias 'study-android'
            keyPassword '12345'
            storeFile file('../.keystore/study-android.keystore')
            storePassword '12345'
        }
    }

    buildTypes {
        release {
            ...
            signingConfig signingConfigs.release
        }
    }
}
```