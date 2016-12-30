package com.demosqcl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/*
* 自定义控件基础学习
*
* */
public class CustomActivity2 extends Activity {

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom2);
        type = getIntent().getIntExtra("custom_type", 0);
//        type = 3;
        initView(type);

    }

    private void initView(int type) {
        switch (type) {
            case 1://蜘蛛网雷达网芝麻信用分效果
                findViewById(R.id.tyep001).setVisibility(View.VISIBLE);
                break;
            case 2://二阶贝塞尔曲线演示
                findViewById(R.id.tyep002).setVisibility(View.VISIBLE);
                break;
            case 3://三阶贝塞尔曲线演示
                findViewById(R.id.tyep003).setVisibility(View.VISIBLE);
                break;
        }


    }


}
