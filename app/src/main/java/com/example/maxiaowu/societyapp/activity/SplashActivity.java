package com.example.maxiaowu.societyapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.inject.IntentParamInject;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.router.RouterService;
import com.iqiyi.maxiaowu.router_compiler.Router;

public class SplashActivity extends AppCompatActivity {

    public  @IntentParamInject
    String tag;

    private Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

                Router router = new Router(SplashActivity.this);
                RouterService service = router.create(RouterService.class);
                service.startMainActivity("","desc");
                overridePendingTransition(0, R.anim.splash_activity_exit_anim);
                finish();
            }
        },1000);

    }

}
