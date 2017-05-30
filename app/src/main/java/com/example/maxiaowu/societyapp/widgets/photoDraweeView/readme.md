#### PhotoDraweeView实现
##### 1. 实现双击放大缩小
    GestureDetector检测双击手势  
    检测图片边界
##### 2.实现图片缩放
    ScaleGestureDetector检测缩放手势(注意被复写的函数的返回值)
    缩放动画的实现
##### 3.图片的拖拽
     多指触控时,确定ActivePoniter
     拖拽时处理滑动冲突
#### 4.实现Fling手势,图片的滑动
     GestureDetector检测fling手势
     Scroller.fling()实现图片View的滑动