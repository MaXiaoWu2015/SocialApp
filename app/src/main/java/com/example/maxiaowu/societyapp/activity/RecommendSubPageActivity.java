package com.example.maxiaowu.societyapp.activity;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.Window;
import android.widget.ImageView;

import com.example.maxiaowu.societyapp.R;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

public class RecommendSubPageActivity extends AppCompatActivity {

    private SimpleDraweeView mHeaderIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_recommend_sub_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //1.为什么Fresco不兼容默认的transition动画ChangeImageTransform,转换后DraweeView不显示
            //Fresco tips:使用默认的ChangeImageTransform是没有效果的,因为ChangeImageTransform是通过改变ImageView的matrix进行缩放的,而Fresco
            //不支持matrix类型的ScaleType,Fresco提供了focusCrop作为补充,内部维护着自己的转换Matrix
            //TODO:了解动画实现原理, Animation框架会调用applyTransform()------->DraweeTransition

            //Animation动画流程:View.startAnimation(Animation)-------->给View#mCurrentAnimation赋值,invalidate(true)-------
            //--->draw()---------->applyLegacyAnimation()------>animation.getTransformation()------->animation.applyTransformation()(每个Animation子类重写)

            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER, ScalingUtils.ScaleType.CENTER));
        }
        mHeaderIcon = findViewById(R.id.image_transition);
        ViewCompat.setTransitionName(mHeaderIcon,"recommend_songlist_poster");
        String iconurl = getIntent().getExtras().getString("recommend_icon_url");
        mHeaderIcon.setImageURI(Uri.parse(iconurl));

    }
}
