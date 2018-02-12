package com.example.maxiaowu.societyapp.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

/**
 * Created by matingting on 2018/2/5.
 */

public class PermissionUtils {

    public static boolean hasPermissions(Context context,String... permissions){

        //Always return true for sdk<M,let the system deal with permissions
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            return true;
        }


        if (context == null){
            throw new IllegalArgumentException("Can't check permission for null context");
        }


        for (String value:permissions){
            if (ContextCompat.checkSelfPermission(context,value) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }


    public static void requestPermissions(Activity context, String permission,int requestCode){

    }

    public static void requestPermissions(PermissionRequest request,Activity activity){
        if (hasPermissions(activity,request.getPerms())){
            Toast.makeText(activity,"权限已经开启",Toast.LENGTH_SHORT).show();
        }else{
            request.getmHelper().requestPermissions(request.getmRationale(),request.getmPositiveButtonText(),
                    request.getmNegativeButtonText(),request.getmTheme(),request.getmRequestCode(),request.getPerms());
        }

    }


}
