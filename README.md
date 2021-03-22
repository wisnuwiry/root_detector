# Root Detector

A simple library to check root access in device.

These are the current checks/tricks we are using to give an indication of root.

- checkIsRoot
- checkIsRootedWithBusyBox (only Android)


## Usage

The first step you need to install this dependency into pubspec.yaml in your Flutter project.

```yaml
dependencies:
  root_detector: // recommended to use lastest version
```

### checkIsRoot

```dart
try {
 final result = await RootDetector.isRooted; // type data is bool
 return result;
} on PlatformException catch(e){
  // TODO: handling your error, whenever have error from native code
}
```

### checkIsRootedWithBusyBox

```dart
try {
 final result = await RootDetector.checkIsRootedWithBusyBox; // type data is bool
 return result;
} on PlatformException catch(e){
 // TODO: handling your error, whenever have error from native code
}
```

## Credit

- [RootBeer](https://github.com/scottyab/rootbeer)