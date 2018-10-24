package io.xiaoyan.wechatandqq;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** WechatAndQqPlugin */
public class WechatAndQqPlugin implements MethodCallHandler, PluginRegistry.ActivityResultListener {
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "wechat_and_qq");
    final WechatAndQqPlugin instance = new WechatAndQqPlugin((FlutterActivity) registrar.activity());
    registrar.addActivityResultListener(instance);
    channel.setMethodCallHandler(instance);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    pendingResult = result;
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("qqLogin")) {
      startLoginView();
    } else {
      result.notImplemented();
    }
  }

  private void startLoginView() {
    final Intent intent = new Intent(activity, AuthActivity.class);
    activity.startActivityForResult(intent, REQUEST_CODE_QQ_LOGIN);
  }

  public static final int REQUEST_CODE_QQ_LOGIN = 4567;

  private FlutterActivity activity;
  private Result pendingResult;
  private Map<String, Object> arguments;

  public WechatAndQqPlugin(FlutterActivity activity) {
    this.activity = activity;
  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_QQ_LOGIN) {
      if (resultCode == Activity.RESULT_OK) {
        final String openId = data.getStringExtra(AuthActivity.OPEN_ID);
        final String accessToken = data.getStringExtra(AuthActivity.ACCESS_TOKEN);
        final Map<String, String> map = new HashMap<>();
        map.put(AuthActivity.OPEN_ID, openId);
        map.put(AuthActivity.ACCESS_TOKEN, accessToken);
        pendingResult.success(map);
      } else {
        pendingResult.success(null);
      }
      return true;
    } else {
      return false;
    }
  }
}
