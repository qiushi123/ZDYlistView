package com.demosqcl.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/*
* 自定义控件的绘制流程
* 这里简单画一个圆形,下面是绘制步骤
1	构造函数	初始化(初始化画笔Paint)
2	onMeasure	测量View的大小(暂时不用关心)
3	onSizeChanged	确定View大小(记录当前View的宽高)
4	onLayout	确定子View布局(无子View，不关心)
5	onDraw	实际绘制内容(绘制饼状图)
6	提供接口	提供接口(提供设置数据的接口)
*
* */
public class CustomView1 extends View {
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public CustomView1(Context context) {
        this(context, null);
    }

    public CustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLACK);        // 设置颜色
        mPaint.setStyle(Paint.Style.STROKE);   // 设置样式
        mPaint.setTextSize(50f);              // 设置字体大小
    }

    //确定View大小(记录当前View的宽高)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心(宽高数据在onSizeChanged中获取)
        Path path = new Path();                     // 创建Path
        path.lineTo(200, 200);                      // lineTo，第一次从原点开始
        path.lineTo(200,0);
        canvas.drawPath(path, mPaint);              // 绘制Path
    }
}