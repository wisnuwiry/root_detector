package space.wisnuwiry.root_detector

import android.content.Context
import android.os.Build
import com.scottyab.rootbeer.RootBeer
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** RootDetectorPlugin */
class RootDetectorPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var rootBeer: RootBeer

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "space.wisnuwiry/root_detector")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
        rootBeer = RootBeer(context)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        val ignoreSimulator = call.arguments == true

        if (call.method == "checkIsRooted") {
            checkIsRooted(result, ignoreSimulator)
        } else if (call.method == "checkIsRootedWithBusyBox") {
            checkIsRootedWithBusyBox(result, ignoreSimulator)
        } else {
            result.notImplemented()
        }
    }


    // Get check root status in normal devices
    private fun checkIsRooted(result: Result, ignoreSimulator: Boolean) {
        try {
            val isRoot = rootBeer.isRooted
            if (isRoot && ignoreSimulator && isEmulator()) {
                result.success(false)
            } else {
                result.success(isRoot)
            }
        } catch (e: IllegalArgumentException) {
            result.error(e.message.toString(), e.message, e.stackTrace)
        }
    }

    // Get check root status in devices have the busybox binary
    // Example devices have a busybox:
    // - All OnePlus Devices
    // - Moto E
    // - OPPO R9m (ColorOS 3.0,Android 5.1,Android security patch January 5, 2018 )
    private fun checkIsRootedWithBusyBox(result: Result, ignoreSimulator: Boolean) {
        try {
            val isRoot = rootBeer.isRootedWithBusyBoxCheck
            if (isRoot && ignoreSimulator && isEmulator()) {
                result.success(false)
            } else {
                result.success(isRoot)
            }
        } catch (e: IllegalArgumentException) {
            result.error(e.message.toString(), e.message, e.stackTrace)
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {}

    override fun onDetachedFromActivityForConfigChanges() {}

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {}

    override fun onDetachedFromActivity() {}

    /**
     * A simple emulator-detection based on the flutter tools detection logic and a couple of legacy
     * detection systems
     */
    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }
}
