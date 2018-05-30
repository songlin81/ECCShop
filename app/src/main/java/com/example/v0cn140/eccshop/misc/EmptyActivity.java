package com.example.v0cn140.eccshop.misc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.v0cn140.eccshop.R;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmptyActivity extends AppCompatActivity {

    private Button httpRequest;
    private TextView mTextView;
    private Handler mHandler;
    private String mMessage;

    private ProgressBar secondBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        httpRequest = (Button) findViewById(R.id.httpRequest);
        mTextView = (TextView) findViewById(R.id.tvInfo);
        secondBar = (ProgressBar)findViewById(R.id.secondBar);

        httpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynToPortal();
                secondBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void AsynToPortal(){

        mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getString(R.string.webAPI))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.toString();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        secondBar.setVisibility(View.GONE);
                        mTextView.setText(mMessage);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMessage = response.toString();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        secondBar.setVisibility(View.GONE);
                        mTextView.setText(mMessage);
                    }
                });
            }
        });
    }
}
