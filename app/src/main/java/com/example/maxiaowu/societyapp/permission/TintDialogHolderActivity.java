package com.example.maxiaowu.societyapp.permission;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.maxiaowu.societyapp.App;
import com.example.maxiaowu.societyapp.permission.dialog.TintDialogFragment;
import com.google.common.base.MoreObjects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by matingting on 2018/2/6.
 * 利用Activity作为对话框的载体,这样就可以接收到来自设置页面的结果了
 *
 */

public class TintDialogHolderActivity extends AppCompatActivity implements View.OnClickListener{

    private TintDialogFragment dialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogFragment = new TintDialogFragment();
        dialogFragment.setListener(this);
        dialogFragment.show(getSupportFragmentManager(),"");
    }
    private void openNotification() {
        Intent localIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName",getPackageName());
        }
        startActivityForResult(localIntent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (PermissionUtils.hasPermissions(this, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)) {
                Toast.makeText(this, "dialog change", Toast.LENGTH_SHORT).show();
                addCalendarReminder(this);
//                if (NotificationManagerCompat.from(this).areNotificationsEnabled()){
//                    Toast.makeText(this,"通知是已经开启",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(this,"通知是已经关闭",Toast.LENGTH_LONG).show();
//                }
//            }


            }
        }
    }

    public void addCalendarReminder(Context context){
        long calendarId = getCustomCalendarId(this);
        if (calendarId < 0){
            calendarId = addCalendarAcount(this);
        }


        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART,System.currentTimeMillis()+15*1000*60*10);
        values.put(CalendarContract.Events.DTEND,System.currentTimeMillis()+15*1000*60*10);
        values.put(CalendarContract.Events.TITLE,"#明星来了#");
        values.put(CalendarContract.Events.DESCRIPTION,"");
        values.put(CalendarContract.Events.CALENDAR_ID,calendarId);
        values.put(CalendarContract.Events.HAS_ALARM,0);

        values.put(CalendarContract.Events.ALL_DAY,0);
        values.put(CalendarContract.Events.EVENT_TIMEZONE,TimeZone.getDefault().getID());


        Uri eventUri = CalendarContract.Events.CONTENT_URI;

        Uri result = getContentResolver().insert(eventUri,values);

        long eventID = ContentUris.parseId(result);


        ContentValues reminderValues = new ContentValues();
        reminderValues.put(CalendarContract.Reminders.EVENT_ID,eventID);
        reminderValues.put(CalendarContract.Reminders.MINUTES,14);
        reminderValues.put(CalendarContract.Reminders.METHOD,CalendarContract.Reminders.METHOD_ALERT);

        Uri remindersUri = getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI,reminderValues);

        if (remindersUri != null){
            Toast.makeText(this,"插入日历提醒成功", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"插入日历提醒失败", Toast.LENGTH_LONG).show();

        }
    }



    private long addCalendarAcount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.NAME,"爱奇艺账户");
        values.put(CalendarContract.Calendars.ACCOUNT_NAME,"本地账户");
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE,CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,"爱奇艺");
        values.put(CalendarContract.Calendars.VISIBLE,1);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.SYNC_EVENTS,1);
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE,timeZone.getID());
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT,"本地账户");
        values.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;

        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "本地账户")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri,values);

        long id = result == null ? -1 : ContentUris.parseId(result);

        return id;
    }


    private long getCustomCalendarId(Context context){

        String[] projection= new String[]{CalendarContract.Calendars._ID};

        StringBuilder selection = new StringBuilder();
        selection.append("((")
                .append(CalendarContract.Calendars.NAME).append(" = ?) AND (")
                .append(CalendarContract.Calendars.ACCOUNT_NAME).append(" = ?) AND (")
                .append(CalendarContract.Calendars.ACCOUNT_TYPE).append(" = ?))");

        String[] selectionArgs = {"爱奇艺账户","本地账户",CalendarContract.ACCOUNT_TYPE_LOCAL};

        Cursor cursor = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                projection,selection.toString(),selectionArgs,null);
        try{
            if (cursor == null){
                return -1;
            }

            if (cursor.moveToFirst()){
                return cursor.getLong(0);
            }
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return -1;

    }


    @Override
    public void onClick(View v) {
        openNotification();
    }
}












