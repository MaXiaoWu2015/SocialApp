package com.example.maxiaowu.societyapp.widgets.custom_view_demo;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
    private static final float C = 0.551915024494f;
    private Paint mPaint;

    private float[] pts = {10,10,200,20,30,30,40,40,500,50,60,600,70,70,800,80,90,90};
    private Path mPath;

    private PointF mCenter = new PointF(0,0);
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius*C;        // 圆形的控制点与数据点的差值

    private float[] mData = new float[8];               // 顺时针记录绘制圆形的四个数据点
    private float[] mCtrl = new float[16];              // 顺时针记录绘制圆形的八个控制点

    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration/mCount;            // 每一份的时长
    private int mCenterX, mCenterY;


    public CustomView(Context context) {
            super(context);
            init();
        }

        public CustomView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        public void init(){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPath = new Path();


            // 初始化数据点

            mData[0] = 0;
            mData[1] = mCircleRadius;

            mData[2] = mCircleRadius;
            mData[3] = 0;

            mData[4] = 0;
            mData[5] = -mCircleRadius;

            mData[6] = -mCircleRadius;
            mData[7] = 0;

            // 初始化控制点

            mCtrl[0]  = mData[0]+mDifference;
            mCtrl[1]  = mData[1];

            mCtrl[2]  = mData[2];
            mCtrl[3]  = mData[3]+mDifference;

            mCtrl[4]  = mData[2];
            mCtrl[5]  = mData[3]-mDifference;

            mCtrl[6]  = mData[4]+mDifference;
            mCtrl[7]  = mData[5];

            mCtrl[8]  = mData[4]-mDifference;
            mCtrl[9]  = mData[5];

            mCtrl[10] = mData[6];
            mCtrl[11] = mData[7]-mDifference;

            mCtrl[12] = mData[6];
            mCtrl[13] = mData[7]+mDifference;

            mCtrl[14] = mData[0]-mDifference;
            mCtrl[15] = mData[1];

        }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       mCenterX = w/2;
       mCenterY = h/2;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setColor(Color.BLUE);
//            mPaint.setStrokeWidth(100);
//
//            canvas.drawCircle(300,300,100,mPaint);
//            mPaint.reset();
//
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(2);
//            mPaint.setColor(Color.RED);
//            canvas.drawRect(150,150,450,450,mPaint);
//            mPaint.reset();
//
//
//            mPaint.setColor(Color.BLACK);
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            mPaint.setStrokeWidth(30);
//            canvas.drawPoints(pts,mPaint);
//
//            mPaint.reset();
//
//            mPaint.setColor(Color.RED);
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(2);
////            canvas.drawOval(100,150,500,450,mPaint);
//            RectF rect = new RectF(100,150,500,450);
//            canvas.drawOval(rect,mPaint);
//
//
//            mPaint.reset();
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(5);
//            mPaint.setColor(Color.BLUE);
//            canvas.drawRoundRect(200,500,1000,1000,150,80,mPaint);
//
//            canvas.drawArc(200,1200,500,1400,45,80,false,mPaint);
////            mPaint.setColor(Color.BLACK);
////            canvas.drawOval(200,1200,500,1400,mPaint);
//
//
//
//            mPath.moveTo(500,500);
//            mPath.lineTo(700,800);
//            mPath.arcTo(700,800,1000,900,0,-90,false);//直线连接到弧的起点,有痕迹
//            canvas.drawOval(700,800,1000,900,mPaint);
//            mPaint.setColor(Color.parseColor("#ff00ff"));
//            canvas.drawPath(mPath,mPaint);


//            mPaint.reset();
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first);
//            canvas.drawBitmap(bitmap,new Matrix(),mPaint);
//            canvas.drawBitmap(bitmap,new Rect(100,100,500,400),new Rect(200,200,400,500),mPaint);

            //1.heart(如果要画线条比较平滑,还是需要用贝塞尔曲线)
//            mPath.moveTo(500,300);
//            mPath.addCircle(450,250,50, Path.Direction.CW);
////            mPath.arcTo(200,300,400,500,-135,120,true);
//            mPath.addCircle(550,250,50, Path.Direction.CW);
//            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawPath(mPath,mPaint);
//            canvas.drawArc(300,250,700,650,-118,56,true,mPaint);

           //贝塞尔曲线绘制心形
           drawCoordinateSystem(canvas);

//           canvas.translate(mCenterX,mCenterY);
//           canvas.scale(1,-1);


           drawControlAndData(canvas);

           mPaint.setColor(Color.RED);
           mPaint.setStrokeWidth(8);
           mPath.reset();
        mPath.moveTo(mData[0],mData[1]);
        mPath.cubicTo(mCtrl[0],mCtrl[1],mCtrl[2],mCtrl[3],mData[2],mData[3]);
        mPath.cubicTo(mCtrl[4],  mCtrl[5],  mCtrl[6],  mCtrl[7], mData[4], mData[5]);
        mPath.cubicTo(mCtrl[8],  mCtrl[9],  mCtrl[10], mCtrl[11],mData[6], mData[7]);
        mPath.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15],mData[0], mData[1]);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath,mPaint);

           mCurrent +=mPiece;
           if (mCurrent < mDuration){
               mData[1]-=120/mCount;
               mCtrl[7]+=80/mCount;
               mCtrl[9]+=80/mCount;

               mCtrl[4]-=20/mCount;
               mCtrl[10]+=20/mCount;
               postInvalidateDelayed((long) mPiece);
           }



            //2.直方图
//            mPaint.setStyle(Paint.Style.STROKE);
//            canvas.drawLine(100,50,100,650,mPaint);
//            canvas.drawLine(100,650,800,650,mPaint);
//            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//            mPaint.setColor(Color.parseColor("#00ff00"));
//            canvas.drawRect(250,600,350,650, mPaint);
//            canvas.drawRect(375,300,475,650, mPaint);
//            canvas.drawPath(mPath,mPaint);


            //3.扇形图

//            mPaint.setStyle(Paint.Style.FILL);
//            mPaint.setColor(Color.BLUE);
//            canvas.drawArc(300,300,700,700,-43,86,true,mPaint);
//            mPaint.setColor(Color.RED);
//            canvas.drawArc(300,300,700,700,-180,135,true,mPaint);
//            mPaint.setColor(Color.parseColor("#00ff00"));
//            canvas.drawArc(300,300,700,700,45,135,true,mPaint);






        }

    private void drawControlAndData(Canvas canvas) {

            mPaint.setColor(Color.GRAY);
            mPaint.setStrokeWidth(20);
            mPaint.setStrokeCap(Paint.Cap.SQUARE);
        for (int i=0; i<8; i+=2){
            canvas.drawPoint(mData[i],mData[i+1], mPaint);
        }

        for (int i=0; i<16; i+=2){
            canvas.drawPoint(mCtrl[i], mCtrl[i+1], mPaint);
        }

            mPaint.reset();
            mPaint.setColor(Color.GRAY);
            mPaint.setStrokeWidth(4);

        for (int i=2, j=2; i<8; i+=2, j+=4){
            canvas.drawLine(mData[i],mData[i+1],mCtrl[j],mCtrl[j+1],mPaint);
            canvas.drawLine(mData[i],mData[i+1],mCtrl[j+2],mCtrl[j+3],mPaint);
        }
        canvas.drawLine(mData[0],mData[1],mCtrl[0],mCtrl[1],mPaint);
        canvas.drawLine(mData[0],mData[1],mCtrl[14],mCtrl[15],mPaint);




    }

    private void drawCoordinateSystem(Canvas canvas) {
//            canvas.save();
            canvas.translate(mCenterX,mCenterY);
            canvas.scale(1,-1);

            canvas.drawLine(-2000,0,2000,0,mPaint);
            canvas.drawLine(0,-2000,0,2000,mPaint);

//            canvas.restore();




    }
}