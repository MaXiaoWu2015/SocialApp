####知识点
    1.DrawerLayout Android官方侧滑控件
    2.将状态栏的颜色设置为和app主题一样的颜色
    3.home键和back键的区别:
        按下home键,任务栈最上层的Activity会进入stop状态,当再次点击桌面上的应用图标时,任务栈最上层的Activity
      会执行onResume,此时不会重新启动程序,会显示任务栈最上层的Activity
        按下back键,会将任务栈最上层Activity弹出
    4.overridePendingTransition:
        必须在主线程才有效果
        必需紧挨startActivity()或者finish()函数之后调用仅仅android2.0以及以上版本上适用
    5.Matrix相关函数详解:http://blog.csdn.net/cquwentao/article/details/51445269
    6.两个手势检测器  GestureDetector
    7.新曲Fragment,推荐模块,动态添加View
            1)LayoutInflater.from(context).inflate 加载布局   三个不同参数的重载的区别
            2)ViewGroup.addView(View,LayoutParams)源码实现----->引申出:为什么不能在draw相关方法中调用addView or removeView  ??????
    8.spannableString

#### 遗留问题
        1.Android 5.0以上实现Activity转场动画新方式
        2.MagicaSakura Android多主题框架
####命名风格
    1.控件id，空间名字缩写_功能模块_作用
    

