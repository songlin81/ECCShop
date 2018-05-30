package com.example.v0cn140.eccshop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.v0cn140.eccshop.pojo.MessageEvent;
import com.example.v0cn140.eccshop.utility.alipay.OrderInfoUtil;
import com.example.v0cn140.eccshop.utility.alipay.PayResult;
import com.example.v0cn140.eccshop.utility.log.LogManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class AlipayActivity extends FragmentActivity {

    public static final String APPID = "2016081600259819";
    public static final String PID = "2088102171457794";
    public static final String TARGET_ID = "optionalContents";

    // RSA2_PRIVATE(high prio) or RSA_PRIVATE, either is okay
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnACDZjlAQOXSC" +
            "HIHmNuLtlEOUz8IC7teClPoSwTINHfPkfjmBQUf6arw6g691khsFdziI5ZRSg3jg" +
            "MHoQImEzXU9n+5bsEbNlYo3EzsYf/bM1P8ktOe+CXQFSyiiFaFnHbw4rpRpKQ0nv" +
            "qM3nduyrGkymh/dKdZsj0P+uuqNNtel7JHwffuw4zP2/ss/CLv6owIjyODGwQ9qz" +
            "WuwnE95kv33ye30y5KMaKek1tCp+Tbde5HKEJYwYFXMnJmD0XvLhPKdQ2H8HSHcA" +
            "68a0cKwgQ76UhU3qN2FmuuSyGCD+GD7954fq7e3rc4zNH+v5hiakuo1VRzP/aT7c" +
            "nn2bn0slAgMBAAECggEALFAeZ1wZ5pQ4VCYYWewKfxwuaphOVNTnnqDFUqCtFUnz" +
            "Y5gSzjdyUAJue+BheIMSZHrkJ38zwklck7CJUyqsPCLwZRkfTPjNb4OdNrHfZLG/" +
            "EcwhJ70Nzru7FCrMYcn0uLTBP9eJiFcekVdre5XU6voJiM7fUYSva+uTTNv9ZSsI" +
            "EvKxaM+CUs1A4fsp5g+ocARGRKFpkFmbn1Uoc/QOx5RMa5KMBZVbqMxSzax0rlTG" +
            "qhylm5ClDEkUEgwRiWNGeyqmIZ50p5Q+Ai+E0KMnuskZ1xJB4NsIgq5eHTCO8IQE" +
            "QtcvTLTguVi9P8X4pz0oDNCLekD1tze7OZNM2HDUaQKBgQDdWFBOpHlDDUZkKFdv" +
            "zshly3balXYyvfbtui3vdh0KJU/kn4pO7oLa6O4Kz0MPcdC4SfDY91A5AIHYIVt9" +
            "HecDAznvVEDEd3RNhsy5Y5IM1X6b3pJroOvnc5lc8pPLWsU3gBVIfehDQ891xq0d" +
            "j8UvHP7CwmU7Wx8rye5C5OdNdwKBgQDBJaTb2Dj9yEhGhD+Nvq0iIPcIWpVybB13" +
            "GjLKVCeiLASEgp/O9fUBEoag3bGUMR9TesXZyjdySYI6uuO3RTBOVzyr2kEinC5o" +
            "kL1oLBjD471S/lD3nZMPSOmjIslBv6nBK76blOJfsRIEtGAEEYSuA3Di0ofCwoje" +
            "Q44C5SdjQwKBgF/kKqZnSsFptt0dPQQLKXYwN0VrCtHgymEjD1N2cA49K+d31otr" +
            "ePUthDLap8rBHBM6tUTJn8+SEK55hREnKzV66FZuwcBbn98WTwrhwBwfQcJ1IinO" +
            "hWS4B/DkhnLK2DW7H31QTpcVgYMcl1L/PgWzIgtt/D1CtLZbvQTt9ebxAoGAfBGk" +
            "TpwcL9khpDp7YJ/lMxEd3j3h1fhhDQrFKXbZZrEDzeLiqN0Rnri5xkfiNCFB0CwD" +
            "P51H9KAHiZZXhHhrSUd8b5/pS0QsA3tGT85TsjFuggtfE1AjESRzABnuUc++SZnB" +
            "/Mqsh2+E9YSwUKy4H9eSAgHet1qi9LNMW8u7e2UCgYEA22N5PGZd3TTcrRZs/8vw" +
            "8mw4Tl4dbMhNHhmGMONV8w8Wz8TeTLlTAW3dO4JOYpQfXcya/dQ0LHe11Fk/guXn" +
            "nF3NpUqcyq96awxDT+YXcTgo5KCi5vSCuNx4ey/m5AmDTNnBae8ncNfG9lQbzZWn" +
            "w4FEm8oGgnVtvBr1foOQ92g=";

    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(AlipayActivity.this, "支付失败"+resultStatus, Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new MessageEvent( "支付失败 "+resultStatus));
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
    }

    public void payment(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
                new AlertDialog.Builder(this)
                        .setTitle("警告")
                        .setMessage("需要配置APPID | RSA_PRIVATE")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                finish();
                            }
                        }).show();
                return;
        }

        // orderInfo & privateKey should from server side
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(AlipayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogManager.getLogger().i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(getBaseContext(), event.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
