package io.xiaoyan.wechatandqq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class AuthActivity extends Activity {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String OPEN_ID = "openid";

    private Tencent tencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tencent = Tencent.createInstance("1106951967", getApplicationContext()); //QQ APP ID
        if (tencent.isSessionValid()) {
            tencent.logout(this);
        }
        if (!tencent.isSessionValid()) {
            tencent.login(this, "get_simple_userinfo", qqListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
    }

    private IUiListener qqListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            try {
                final JSONObject jo = (JSONObject) o;
                final String openId = jo.getString(OPEN_ID);
                final String accessToken = jo.getString(ACCESS_TOKEN);
                if (TextUtils.isEmpty(openId) || TextUtils.isEmpty(accessToken)) {

                } else {
                    final Intent intent = new Intent();
                    intent.putExtra(OPEN_ID, openId);
                    intent.putExtra(ACCESS_TOKEN, openId);
                    setResult(Activity.RESULT_OK, intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            setResult(Activity.RESULT_CANCELED);
        }

        @Override
        public void onCancel() {
            setResult(Activity.RESULT_CANCELED);
        }
    };
}
