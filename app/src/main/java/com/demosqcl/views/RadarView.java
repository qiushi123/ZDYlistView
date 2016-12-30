package com.demosqcl.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/*
* 蜘蛛网，雷达
* */
public class RadarView extends View {
    private int count = 6;                //数据个数  
    private float angle = (float) (Math.PI * 2 / count);//弧度
    private float radius;                   //网格最大半径  
    private int centerX;                  //中心X  
    private int centerY;                  //中心Y  
    private String[] titles = {"电影", "购物", "美食", "出行", "日用", "煤电"};
    private double[] data = {100, 60, 60, 60, 100, 50}; //各维度分值
    private float maxValue = 100;             //数据最大值
    private Paint mainPaint;                //雷达区画笔
    private Paint valuePaint;               //数据区画笔  
    private Paint textPaint;                //文本画笔

    public RadarView(Context context) {
        super(context);
        initPaint();
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /*
    * 初始化画笔
    * */
    private void initPaint() {
        mainPaint = new Paint();
        valuePaint = new Paint();
        textPaint = new Paint();
        mainPaint.setColor(Color.BLACK);        // 设置颜色
        mainPaint.setStyle(Paint.Style.STROKE);   // 设置样式
        mainPaint.setTextSize(20f);

        valuePaint.setColor(Color.RED);        // 设置颜色
        valuePaint.setStyle(Paint.Style.STROKE);   // 设置样式
        valuePaint.setTextSize(20f);

        textPaint.setColor(Color.RED);        // 设置颜色
        textPaint.setStyle(Paint.Style.STROKE);   // 设置样式
        textPaint.setTextSize(40f);              // 设置字体大小
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.7f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);//绘制正多边形
        drawLines(canvas);//绘制直线链接
        drawText(canvas);//绘制文字
        drawRegion(canvas);//绘制区域
    }

    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, 10, valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(127);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if (angle * i >= 0 && angle * i <= Math.PI / 2) {//第4象限
                canvas.drawText(titles[i], x, y, textPaint);
            } else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {//第3象限
                canvas.drawText(titles[i], x, y, textPaint);
            } else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {//第2象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {//第1象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x - dis, y, textPaint);
            }
        }
    }

    /**
     * 绘制正多边形
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i < count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();//reset:重置路径
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);//移动起点
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mainPaint);
        }
    }


    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }
}