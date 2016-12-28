package com.demosqcl.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/*
* 自定义控件通过画笔做一些简单的操作
* 画各种形状
*
* */
public class CustomView2 extends View {
    private Paint mPaint= new Paint();//1，创建画笔

    public CustomView2(Context context) {
        this(context, null);//调用2个构造参数的
    }

    public CustomView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();//画笔的一些操作
    }


    // 2.初始化画笔
    private void initPaint() {
        //2，初始化画笔
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制文字
        // canvas.drawText("This is a canvas", 0, 0, paint);//这样写不会显示文字，因为文字的左下坐标是（0，0）
        canvas.drawText("canvas.drawText坐标为左下（0，150）", 0, 150, mPaint);// (字符，左坐标，下坐标，画笔)

        mPaint.setTextSize(30);//设置画笔大小，即字体大小
        canvas.drawText("canvas.drawText坐标为左下（150，450）", 150, 450, mPaint);// (字符，左坐标，下坐标，画笔)


        //绘制圆点
        canvas.drawPoint(200, 200, mPaint);     //在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
                500, 500,
                500, 600,
                500, 700
        }, mPaint);

        //绘制直线
        //绘制直线需要两个点，初始点和结束点
        canvas.drawLine(300, 300, 500, 600, mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                100, 200, 200, 200,
                100, 300, 200, 300
        }, mPaint);

        //绘制矩形
        /*
        * 关于绘制矩形，Canvas提供了三种重载方法，
        * 第一种就是提供四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形进行绘制。
        * 其余两种是先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)，
        * 三种效果一样
        * 然后传递给Canvas绘制，如下*/
        /*为什么会有Rect和RectF两种？两者有什么区别吗？
            答案当然是存在区别的，两者最大的区别就是精度不同，
            Rect是int(整形)的，
            而RectF是float(单精度浮点型)的*/
        // 第一种
        //        canvas.drawRect(100, 100, 800, 400, mPaint);
        // 第二种
        //        Rect rect = new Rect(100,100,800,400);
        //        canvas.drawRect(rect,mPaint);
        // 第三种
        //        RectF rectF = new RectF(100,100,800,400);
        //        canvas.drawRect(rectF,mPaint);


        /*
        * 绘制圆角矩形：绘制圆角矩形也提供了两种重载方式，如下：
        * */
        // 第一种
        //        RectF rectF = new RectF(100, 100, 800, 400);
        //        canvas.drawRoundRect(rectF, 30, 30, mPaint);
        //        // 第二种
        //        canvas.drawRoundRect(100, 100, 800, 400, 30, 30, mPaint);
        /*上面两种方法绘制效果也是一样的，但鉴于第二种方法在API21的时候才添加上，
        所以我们一般使用的都是第一种。*/


        /*
        * 示例绘制矩形和圆角矩形
        * */
        // 矩形
        //        RectF rectF = new RectF(100, 100, 800, 400);
        //        // 绘制背景矩形
        //        mPaint.setColor(Color.GRAY);
        //        canvas.drawRect(rectF, mPaint);
        //        // 绘制圆角矩形
        //        mPaint.setColor(Color.BLUE);
        //        canvas.drawRoundRect(rectF, 700, 400, mPaint);


        //        绘制椭圆：
        //        相对于绘制圆角矩形，绘制椭圆就简单的多了，因为他只需要一个矩形矩形作为参数:
        // 第一种
        //        RectF rectF = new RectF(100, 100, 800, 400);
        //        canvas.drawOval(rectF, mPaint);
        //        // 第二种
        //        canvas.drawOval(100, 100, 800, 400, mPaint);
        //        //        同样，以上两种方法效果完全一样，但一般使用第一种。


        //绘制圆：
        //绘制圆形也比较简单, 如下：
        //        canvas.drawCircle(500, 500, 400, mPaint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。


        //绘制圆弧学习
        RectF rectF = new RectF(100, 100, 600, 600);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF, mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 90, false, mPaint);//false的时候绘制一个圆弧

        //-------------------------------------
        RectF rectF2 = new RectF(100, 700, 600, 1200);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2, mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2, 0, 90, true, mPaint);//true的时候绘制一个扇形
    }

}  