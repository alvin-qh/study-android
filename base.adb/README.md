# ADB Commands

## ADB Connect

### List connected devices

```sh
$adb devices
```

### Start/Stop service

```sh
$ adb start-server
$ adb stop-server
```

### Connect by USB

```sh
$ adb connect usb
```

### Connect by network

```sh
$ adb connect <ip address>:<port>
```

### Connect to shell

```sh
$ adb shell
```


## Install/Uninstall/Start APP

### Install/Uninstall APK

```sh
$ adb install <-lrstdp> <apk file name>.apk

$ adb uninstall <apk package name>
```

Option arguments:
- none: Default option
- `-l`: Forward lock application
- `-r`: Replace existing application
- `-s`: Install application on sdcard
- `-t`: Allow test packages
- `-d`: Allow version code downgrade
- `-p`: Partial application install


### List all installed APKs

```sh
$ adb shell pm list packages
```

### Show path name of installed APK

```sh
$ adb shell pm <apk package name>
```


### Start APP

```sh
$ adb shell am start -n <apk package name>/<activity class name>
```

### Clear data of one app

```sh
$ adb shell pm clear <apk package name>
```

### Show app version

```sh
$ adb shell dumpsys <apk package name> | grep  versionCode
```


## Data transfer
    
### Download from device

```sh
$ adb pull <source file path on device> [dest file path on computer]
```

### Upload to device

```sh
adb push <source file path on computer> <dest file path on device>
```


## Device information

### Show IP address of device

```sh
$ adb shell netcfg
```

```sh
$ adb shell ifconfig eth0/wlan0
```

### Get resolution ratio of device

```sh
$ adb shell dumpsys window displays
```


## Other

#### Send broadcast

```sh
adb shell am broadcast -a <boradcast action name> -e <extra_key> <extra_value>

```
Arguments:
- `-a`: action name
- `-d`: data uri
- `-t`: mime-type
- `-c`: category
- `-e|--es`: extra key + extra string value
- `--ez`: extra key + extra boolean value
- `--ei`: extra key + extra integer value
- `-n`: component
- `-f`: flags + uri
    
    
#### Logcat
```sh
$ adb logcat  
$ adb logcat *:V[/D/I/W/E/F/S] // with level
```

