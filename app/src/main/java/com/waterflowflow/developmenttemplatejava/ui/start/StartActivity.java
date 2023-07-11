package com.waterflowflow.developmenttemplatejava.ui.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.waterflowflow.developmenttemplatejava.R;
import com.waterflowflow.developmenttemplatejava.ui.main.MainActivity;

public class StartActivity extends AppCompatActivity {

    private int MSG = 1;

    MyHandler handler = new MyHandler();

    private MyThread thread;
    //声明控件
    private Button btnSkip;
    private ImageView ivStartAdvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //找到控件
        btnSkip = findViewById(R.id.btnSkip);
        ivStartAdvert = findViewById(R.id.ivStartAdvert);

        Glide.with(this).load("http://www.waterflowflow.work/MyApplicationMarketServer/startAdvert/Test")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivStartAdvert);

        if(thread == null){
            thread = new MyThread();
            thread.start();
        }


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thread != null){
                    thread.interrupt();
                    thread = null;
                }
                finish();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            int times = 3;//广告持续时间
            while(times  >= 0){
                Message msg = new Message();
                msg.what = MSG;
                msg.arg1 = times;
                handler.sendMessage(msg);
                times--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1: //MSG
                    btnSkip.setText("跳过 "+msg.arg1);
                    if(msg.arg1 <= 0){
                        finish();
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
}