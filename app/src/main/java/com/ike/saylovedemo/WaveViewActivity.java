package com.ike.saylovedemo;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ike.saylovedemo.widget.wave.WaveProgressView;

/**
 * Created by Administrator on 2017/3/13.
 */

public class WaveViewActivity extends AppCompatActivity{
    private String Tag="WaveViewActivity";
    private WaveProgressView wpv;
    private static final int FLAG_ONE = 0X0001;
    private int max_progress = 100;
    private int progress;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            switch (msg.what) {
                case FLAG_ONE:
                    if (progress <= max_progress){
                        wpv.setCurrent(progress, progress + "%");
                        sendEmptyMessageDelayed(FLAG_ONE, 100);
                    }else {
                        return;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_layout);
        initView();
    }
    private void initView() {
        wpv = (WaveProgressView) findViewById(R.id.pv);
        if (wpv==null){
            Log.e(Tag,"wpv==null");
        }
        wpv.setWaveColor("#FF49C12E");
        handler.sendEmptyMessageDelayed(FLAG_ONE, 1000);
    }
}
