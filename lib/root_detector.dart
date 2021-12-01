import 'dart:async';

import 'package:flutter/services.dart';

class RootDetector {
  static const MethodChannel _channel =
      const MethodChannel("space.wisnuwiry/root_detector");

  /// To check status device is rooted or not
  ///
  /// For now this work only in Android, this method can return:
  ///
  /// - `true` => when device is rooted
  /// - `false` => when device is not rooted
  ///
  /// You can do error handling if an error occurs from native code,
  /// and you can catch error code with [PlatformException]
  ///
  /// The following devices are known the have the [busyBox] binary present on the stock rom:
  ///
  /// - All OnePlus Devices
  /// - Moto E
  /// - OPPO R9m (ColorOS 3.0,Android 5.1, Android security patch January 5, 2018 )
  ///
  /// Example for usage:
  ///
  /// ```dart
  /// try {
  ///  final result = await RootDetector.isRooted(); // type data is bool
  ///  return result;
  /// } on PlatformException catch(e){
  ///  // TODO: handling your error, whenever have error from native code
  /// }
  /// ```
  static Future<bool> isRooted({
    /// Params only for Android
    bool busyBox = false,
  }) async {
    if (busyBox == true) {
      return await _channel.invokeMethod('checkIsRootedWithBusyBox');
    }

    final result = await _channel.invokeMethod('checkIsRooted');
    return result;
  }

  /// Use `isRooted(busyBox: true)` method instead of `isRootedWithBusyBox`
  /// and to check jailbreak with busyBox
  ///
  /// Example:
  ///
  /// /// ```dart
  /// try {
  ///  final result = await RootDetector.isRooted(busyBox: true); // type data is bool
  ///  return result;
  /// } on PlatformException catch(e){
  ///  // TODO: handling your error, whenever have error from native code
  /// }
  /// ```
  ///
  @deprecated
  static Future<bool> get isRootedWithBusyBox async {
    final bool result = await _channel.invokeMethod('checkIsRootedWithBusyBox');
    return result;
  }
}
