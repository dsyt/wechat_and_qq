#import "WechatAndQqPlugin.h"
#import <wechat_and_qq/wechat_and_qq-Swift.h>

@implementation WechatAndQqPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWechatAndQqPlugin registerWithRegistrar:registrar];
}
@end
