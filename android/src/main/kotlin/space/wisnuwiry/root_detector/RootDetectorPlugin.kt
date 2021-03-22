package space.wisnuwiry.root_detector

import android.content.Context
import androidx.annotation.NonNull
import com.scottyab.rootbeer.RootBeer

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.lang.IllegalArgumentException

/** RootDetectorPlugin */
class RootDetectorPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var rootBeer: RootBeer

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "space.wisnuwiry/root_detector")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
        rootBeer = RootBeer(context)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "checkIsRooted") {
            checkIsRooted(result)
        } else if (call.method == "checkIsRootedWithBusyBox") {
            checkIsRootedWithBusyBox(result)
        } else {
            result.notImplemented()
        }
    }


    // Get check root status in normal devices
    private fun checkIsRooted(@NonNull result: Result) {
        try {
            val isRoot = rootBeer.isRooted
            result.success(isRoot)
        } catch (e: IllegalArgumentException) {
            result.error(e.message, e.message, e.stackTrace)
        }
    }

    // Get check root status in devices have the busybox binary
    // Example devices have a busybox:
    // - All OnePlus Devices
    // - Moto E
    // - OPPO R9m (ColorOS 3.0,Android 5.1,Android security patch January 5, 2018 )
    private fun checkIsRootedWithBusyBox(@NonNull result: Result) {
        try {
            val isRoot = rootBeer.isRootedWithBusyBoxCheck
            result.success(isRoot)
        } catch (e: IllegalArgumentException) {
            result.error(e.message, e.message, e.stackTrace)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {}

    override fun onDetachedFromActivityForConfigChanges() {}

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {}

    override fun onDetachedFromActivity() {}
}
