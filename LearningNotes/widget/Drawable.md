https://developer.android.com/guide/topics/resources/drawable-resource.html?hl=zh-cn

#### InsetDrawable
>A Drawable that insets another Drawable by a specified distance.
This is used when a view needs a background that is smaller than the View's actual bounds.
```

<?xml version="1.0" encoding="utf-8"?>
<inset xmlns:android="http://schemas.android.com/apk/res/android"
    android:drawable="@color/blue"
    android:insetBottom="40dp"
    android:insetLeft="30dp"
    >
</inset>  
或者
new InsetDrawble(Drawable d,int insetLeft,int insetRigth,int insetTop,int insetBottom)

```
#### TransitionDrawable
>实现两个Drawable之间渐入渐出的效果
```
     TransitionDrawable drawable = (TransitionDrawable) getResources().getDrawable(R.drawable.transition_drawable);
     mHeaderIcon.setImageDrawable(drawable);
     drawable.startTransition(2500);//drawable.reverseTransition(2500)
```
####ScaleDrawable

####ClipDrawable
>ProgressBar实现的基础