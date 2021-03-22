import 'dart:async';

import 'package:flutter/services.dart';

class RootDetector {
  static const MethodChannel _channel =
      const MethodChannel("space.wisnuwiry/root_detector");

  /// TO check status device is rooted or not
  ///
  /// For now this work only in Android, this method can return:
  ///
  /// - `true` => when device is rooted
  /// - `false` => when device is not rooted
  ///
  /// You can do error handling if an error occurs from native code,
  /// and you can catch error code with [PlatformException]
  ///
  /// Example for usage:
  ///
  /// ```dart
  /// try {
  ///  final result = await RootDetector.isRooted; // type data is bool
  ///  return result;
  /// } on PlatformException catch(e){
  ///  // TODO: handling your error, whenever have error from native code
  /// }
  /// ```
  static Future<bool> get isRooted async {
    final bool result = await _channel.invokeMethod('checkIsRooted');
    return result;
  }

  /// To check status device is rooted or not
  ///
  /// This method can return:
  ///
  /// - `true` => when device is rooted
  /// - `false` => when device is not rooted
  ///
  /// Manufacturers often leave the busybox binary in production builds and this
  /// doesn't always mean that a device is root.
  ///
  /// We have removed the busybox check we used to include as standard in the
  /// `isRooted` method to avoid these false positives.
  ///
  /// The following devices are known the have the busybox binary present on the stock rom:
  ///
  /// - All OnePlus Devices
  /// - Moto E
  /// - OPPO R9m (ColorOS 3.0,Android 5.1,Android security patch January 5, 2018 )
  ///
  /// **Warning**: this feature only work in Android only
  ///
  /// Example:
  ///
  /// ```dart
  /// try {
  ///  final result = await RootDetector.isRootedWithBusyBox; // type data is bool
  ///  return result;
  /// } on PlatformException catch(e){
  ///  // TODO: handling your error, whenever have error from native code
  /// }
  /// ```
  static Future<bool> get isRootedWithBusyBox async {
    final bool result = await _channel.invokeMethod('checkIsRootedWithBusyBox');
    return result;
  }
}
