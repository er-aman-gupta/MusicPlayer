package com.example.amanpc.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {
    static TextView textView;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startActivity(new Intent(PermissionActivity.this,SplashActivity.class));
                finish();
            }
            else Toast.makeText(PermissionActivity.this,"Permission Denied\nStart App Again",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        textView=findViewById(R.id.myText);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        if(ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {startActivity(new Intent(PermissionActivity.this,SplashActivity.class));
            finish();

        }

        if(ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }


        /*if(ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_DENIED){
            TextView textView=findViewById(R.id.myText);
            textView.setText("Permissions Denied");
        }*/


    }
}
