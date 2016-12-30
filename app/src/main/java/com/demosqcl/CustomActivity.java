package com.demosqcl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
* 自定义控件基础学习
*
* */
public class CustomActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        findViewById(R.id.button000).setOnClickListener(this);
        findViewById(R.id.button001).setOnClickListener(this);
        findViewById(R.id.button002).setOnClickListener(this);
        findViewById(R.id.button003).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button000://listview吸顶效果
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button001://蜘蛛网雷达网芝麻信用分效果
                intent = new Intent(this, CustomActivity2.class);
                intent.putExtra("custom_type", 1);
                startActivity(intent);
                break;
            case R.id.button002://二阶贝塞尔曲线演示
                intent = new Intent(this, CustomActivity2.class);
                intent.putExtra("custom_type", 2);
                startActivity(intent);
                break;
            case R.id.button003://三阶贝塞尔曲线演示
                intent = new Intent(this, CustomActivity2.class);
                intent.putExtra("custom_type", 3);
                startActivity(intent);
                break;


        }

    }
}
