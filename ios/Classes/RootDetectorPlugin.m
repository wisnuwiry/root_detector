#import "RootDetectorPlugin.h"
#if __has_include(<root_detector/root_detector-Swift.h>)
#import <root_detector/root_detector-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "root_detector-Swift.h"
#endif

@implementation RootDetectorPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftRootDetectorPlugin registerWithRegistrar:registrar];
}
@end
