package com.jokerpeng.demo.qqlogindemo;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private int index;
    private boolean isPlayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAction();
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
    }

    private void initData() {
        isPlayed = false;
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
    }

    private void initAction() {

    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        index = mediaPlayer.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(isPlayed){
            mediaPlayer.seekTo(index);
            mediaPlayer.start();
        }else{
            mediaPlayer.start();
            isPlayed = true;
        }

    }

    private void start(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceView.getHolder());
            AssetManager assetManager = getAssets();
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("test1.mp4");
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0,0);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
