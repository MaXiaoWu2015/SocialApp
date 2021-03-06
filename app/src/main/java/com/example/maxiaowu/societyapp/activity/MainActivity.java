package com.example.maxiaowu.societyapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.inject.IntentParamInject;
import com.example.maxiaowu.societyapp.R;
import com.example.maxiaowu.societyapp.adapter.DrawerLayoutAdpater;
import com.example.maxiaowu.societyapp.adapter.MainContentViewPagerAdapter;
import com.example.maxiaowu.societyapp.adapter.model.DrawerLayoutItem;
import com.example.maxiaowu.societyapp.fragment.MyMusicFragment;
import com.example.maxiaowu.societyapp.fragment.NetMusicFragment;
import com.example.maxiaowu.societyapp.utils.SystemUIUtils;
import com.iqiyi.maxiaowu.router_compiler.RouterInjector;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

     @IntentParamInject("desc")
     String desc;

     @IntentParamInject("age")
     int age;


    private ActionBar mActionbar;
    private ListView mLeftMenuLv;
    private DrawerLayout mDrawerLayout;
    private View mLeftMenuHeaderView;
    private DrawerLayoutAdpater mDrawerLayoutAdapter;
    private ArrayList<DrawerLayoutItem> mSlideMenuLists=new ArrayList<>( Arrays.asList( new DrawerLayoutItem(R.drawable.topmenu_icn_night, "夜间模式"),
            new DrawerLayoutItem(R.drawable.topmenu_icn_skin, "主题换肤"),
            new DrawerLayoutItem(R.drawable.topmenu_icn_time, "定时关闭音乐"),
            new DrawerLayoutItem(R.drawable.topmenu_icn_vip, "下载歌曲品质"),
            new DrawerLayoutItem(R.drawable.topmenu_icn_exit, "退出")));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        RouterInjector.inject(this);
//        new MainActivity_RouterInjecting<MainActivity>(this);
        initView();
    }

    private void initView() {
        initToolbar();
        initLeftMenu();
        initMainContent();
    }

    private void initMainContent() {
         ViewPager mVPMainContent= (ViewPager) findViewById(R.id.vp_main_content);
        MainContentViewPagerAdapter mVPAdapter=new MainContentViewPagerAdapter(getSupportFragmentManager());
        mVPAdapter.addFragment(NetMusicFragment.newInstance());
        mVPAdapter.addFragment(MyMusicFragment.newInstance());
        mVPMainContent.setAdapter(mVPAdapter);
        mVPMainContent.setCurrentItem(0);
    }

    private void initToolbar() {

        Toolbar toolbar= (Toolbar) findViewById(R.id.tl_toolbar);
        //将toolbar设置为Activity的应用栏后,就可访问Actionbar类提供的方法
        setSupportActionBar(toolbar);
        //要使用 ActionBar 实用方法，请调用 Activity 的 getSupportActionBar() 方法。此方法将返回对
        // appcompat ActionBar 对象的引用。获得该引用后，您就可以调用任何一个 ActionBar 方法来调整应用栏。
        // 例如，要隐藏应用栏，请调用 ActionBar.hide()。
        mActionbar=getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);//enable the up button
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_menu);//如果不设置图片，则使用主题默认的
        mActionbar.setDisplayShowTitleEnabled(false);

        SystemUIUtils.changeStatusBarColor(this,getResources().getColor(R.color.theme_color_primary));
    }

    private void initLeftMenu() {
        //这种方法也可以
//        ImageView mLeftMenuHeaderViewIv = new ImageView(this);
//        mLeftMenuHeaderViewIv.setBackgroundResource(R.mipmap.topinfo_ban_bg);
//        AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,360);
//        mLeftMenuHeaderViewIv.setLayoutParams(params);
  //Listview设置Header、Footer注意:
        //1.不调用setAdapter函数，HeaderView和FooterView都不会显示，即使调用setAdapter(null)，也可以.因为ListView是在setAdater()函数
        //中处理HeaderView和FooterView的集合，然后wrap adapter的
        //2.由于API17(4.2)之前，addHeaderView()函数,如果adapter不为null会报异常，在API17之后则对adapter不为null做了处理,所以为了保持兼容性
        //尽量在setAdater之前调用addHeaderView和addFooterView
        mLeftMenuLv= (ListView) findViewById(R.id.lv_left_slide_menu);
        mLeftMenuHeaderView= LayoutInflater.from(this).inflate(R.layout.slide_menu_listview_header,mLeftMenuLv,false);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.dl_left_menu);
        mDrawerLayout.setStatusBarBackgroundColor(Color.TRANSPARENT);
        mLeftMenuLv.addHeaderView(mLeftMenuHeaderView);
        mLeftMenuLv.setAdapter(mDrawerLayoutAdapter=new DrawerLayoutAdpater(this,mSlideMenuLists));
        mLeftMenuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {//按下back键时的回调
        //实现和home键一样的效果----启动launcher
//        super.onBackPressed();  一定要注释掉
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
