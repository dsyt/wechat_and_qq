import 'dart:async';

import 'package:flutter/services.dart';

class WechatAndQq {
  static const MethodChannel _channel =
      const MethodChannel('wechat_and_qq');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<Map<String, String>> qqLogin() async {
    return await _channel.invokeMethod("qqLogin");
  }
}
