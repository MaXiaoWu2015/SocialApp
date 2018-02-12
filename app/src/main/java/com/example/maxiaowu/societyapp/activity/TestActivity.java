package com.example.maxiaowu.societyapp.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.permission.PermissionRequest;
import com.example.maxiaowu.societyapp.permission.PermissionUtils;
import com.example.maxiaowu.societyapp.permission.TintDialogHolderActivity;
import com.example.maxiaowu.societyapp.permission.dialog.TintDialogFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void requestPermission(View view) {

        startActivity(new Intent(this,Test2Activity.class));

//
//        if (PermissionUtils.hasPermissions(this,Manifest.permission.READ_CALENDAR)){
//            Toast.makeText(this,"open the camera",Toast.LENGTH_SHORT).show();
//        }else{
//            PermissionUtils.requestPermissions(new PermissionRequest.Builder( this,0,
//                   new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR} ).build(),this);
//        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                Toast.makeText(this,"权限已经开启",Toast.LENGTH_LONG).show();





                break;
            default:
        }
    }

    public void showDialog(View view) {

        startActivity(new Intent(this,TintDialogHolderActivity.class));
    }
}
