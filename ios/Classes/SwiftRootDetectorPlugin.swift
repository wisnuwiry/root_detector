import Flutter
import UIKit

public class SwiftRootDetectorPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "space.wisnuwiry/root_detector", binaryMessenger: registrar.messenger())
        let instance = SwiftRootDetectorPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if(call.method == "checkIsRooted"){
            let isJailBroken = UIDevice.current.isJailBroken
            let isSimulator = UIDevice.current.isSimulator
            let args = call.arguments
            
            if let ignoreSimulator = args as? Bool {
                if ignoreSimulator && isSimulator && isJailBroken {
                    result(false)
                }else{
                    result(isJailBroken)
                }
            }else{
                result(isJailBroken)
            }
        }
    }
}
