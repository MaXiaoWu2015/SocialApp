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