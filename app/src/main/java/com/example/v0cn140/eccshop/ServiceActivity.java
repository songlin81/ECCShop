package com.example.v0cn140.eccshop;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.v0cn140.eccshop.utility.version.DialogUtils;
import com.example.v0cn140.eccshop.utility.version.ToastUtils;
import com.example.v0cn140.eccshop.utility.version.UpdateStatus;
import com.example.v0cn140.eccshop.utility.version.UpdateVersionUtil;
import com.example.v0cn140.eccshop.utility.version.UpdateVersionUtil.UpdateListener;
import com.example.v0cn140.eccshop.utility.version.VersionInfo;

public class ServiceActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //本地测试检测是否有新版本发布
                UpdateVersionUtil.localCheckedVersion(ServiceActivity.this, new UpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, final VersionInfo versionInfo) {
                        //判断回调过来的版本检测状态
                        switch (updateStatus) {
                            case UpdateStatus.YES:
                                //弹出更新提示
                                UpdateVersionUtil.showDialog(ServiceActivity.this, versionInfo);
                                break;
                            case UpdateStatus.NO:
                                //没有新版本
                                ToastUtils.showToast(getApplicationContext(), "已经是最新版本了!");
                                break;
                            case UpdateStatus.NOWIFI:
                                //当前是非wifi网络
                                ToastUtils.showToast(getApplicationContext(), "只有在wifi下更新！");

                                DialogUtils.showDialog(ServiceActivity.this, "温馨提示","当前非wifi网络,下载会消耗手机流量!", "确定", "取消", new DialogUtils.DialogOnClickListener() {
                                    @Override
                                    public void btnConfirmClick(Dialog dialog) {
                                        dialog.dismiss();
                                        UpdateVersionUtil.showDialog(ServiceActivity.this, versionInfo);
                                    }

                                    @Override
                                    public void btnCancelClick(Dialog dialog) {
                                        dialog.dismiss();
                                    }
                                });

                                break;
                            case UpdateStatus.ERROR:
                                //检测失败
                                ToastUtils.showToast(getApplicationContext(), "检测失败，请稍后重试！");
                                break;
                            case UpdateStatus.TIMEOUT:
                                //链接超时
                                ToastUtils.showToast(getApplicationContext(), "链接超时，请检查网络设置!");
                                break;
                        }
                    }
                });
            }
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_service);
//    }
//
//    public void startService(View view) {
//        startService(new Intent(getBaseContext(), EcoService.class));
//    }
//
//    // Method to stop the service
//    public void stopService(View view) {
//        stopService(new Intent(getBaseContext(), EcoService.class));
//    }
}
