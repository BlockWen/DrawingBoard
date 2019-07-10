package com.blocki.drawingboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

/**
 * Created By Blocki
 * @date 2019/6/26 9:47
 * @author Blocki
 * 这个类是绘制触摸路径，会将触摸路径记录下来并显示在view上，通过getTouchPathImage()方法可以获取到bitmap
 * 使用了Path的moveTo和lineTo方法绘制线条。
 * 后期准备使用quadTo实现平滑过渡的线条（无锯齿，原理是贝塞尔曲线）
 */
public class TouchPathView extends View {

    private static final String TAG = "TouchPathView";

    private Paint paint;
    private Path path;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    public TouchPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();

    }

    public TouchPathView(Context context) {
        super(context);
        //init();
    }

    /**
     * 初始化paint path canvas bitmap
     * */
    private void init() {

        paint = new Paint();
        //画笔颜色
        paint.setColor(Color.BLACK);
        //画笔样式
        paint.setStyle(Paint.Style.STROKE);
        //画笔粗细
        paint.setStrokeWidth(5);
        //设置是否抗锯齿
        paint.setAntiAlias(true);

        path = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        //填充背景色
        mBitmap.eraseColor(Color.WHITE);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下时
                Log.d(TAG, "onTouchEvent: down");
                path.moveTo(event.getX(),event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: move");
                path.lineTo(event.getX(),event.getY());
                invalidate();//也可使用postInvalidate方法进行重绘（postInvalidate是异步的，也就是使用handler post一个message）
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起时对canvas绘制的view保存为bitmap
                Log.d(TAG, "onTouchEvent: up");
                drawBitMap();
                break;
            default:

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);

        canvas.drawPath(path,paint);

    }

    //绘制到bitmap中
    private void drawBitMap(){
        mCanvas.drawPath(path,paint);
    }

    public void reset(){
        path.reset();
        invalidate();
    }

    /**
     * 获取当前view 的触摸路径图bitMap
     * */
    public Bitmap getBitMap(){
        return mBitmap;
    }


}
