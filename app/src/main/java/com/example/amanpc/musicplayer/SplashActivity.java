package com.example.amanpc.musicplayer;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int time=0;
        if(Player.OneTimeOnly==false){
        ProgressDialog progress=new ProgressDialog(SplashActivity.this);
        progress.setMessage("Loading your music\nPlease wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCancelable(false);
        progress.show();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },100);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*ProgressDialog progress=new ProgressDialog(this);
                progress.setMessage("Downloading Music");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();*/
                while (!Player.songset);
                startActivity(new Intent(SplashActivity.this,Player.class));
                finish();

                }
            }
        ,1000);

     /*   Thread thread=new Thread();
        thread.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyTask myTask=new MyTask();
                myTask.execute("hi");
            }
        },10);
    }*/

            MyTask myTask=new MyTask();
            myTask.setPriority(Thread.MAX_PRIORITY);
            myTask.start();
        }
        else {
            startActivity(new Intent(SplashActivity.this,Player.class));
            finish();
        }
    }
}
