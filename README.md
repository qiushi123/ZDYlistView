# ZDYlistView
滑动listview标题置顶,标题可以置顶的listview,安卓系统状态栏透明,有图有源码

##实现功能
1，listview滑动置顶效果
2，系统时间状态栏透明

##还是先看效果图
![image](https://github.com/qiushi123/ZDYlistView/blob/master/images/GIF.gif?raw=true)

下面说下实现原理
1，时间状态栏透明（这个只能在4.4以后实现）
```java
        /*
        * 4.4以后时间栏状态栏透明
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
```

2，listview上滑吸顶实现
其实吸顶的标题是我们事先写好的textview，放在布局顶部,设置为不可见。然后监听listview的滑动，当listview里的标题所对应的position处于最顶部时，让我们放在顶部的 textview显示出来，并替换里面的文字。
首先看下布局
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <ListView
        android:id="@+id/plv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />

    <TextView
        android:id="@+id/header_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorAccent"
        android:gravity="center_horizontal"
        android:paddingTop="15dp"
        android:text="2016-12-28"
        android:textSize="20sp"/>
</RelativeLayout>
```

然后是监听listview的滑动

```java
/*
        * 添加一个滚动 监听
        * 用来实现标题置顶效果
        * */
        mListView.setOnScrollListener(new ListView.OnScrollListener() {

            //滑动状态监听
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://拖动
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://惯性滑动
                        // 显示滑动时屏幕可见条目中离标题栏最近的第一行
                        int position = mListView.getFirstVisiblePosition();
                        //由于listview添加了一个header，所以position=0代表的是header
                        //这里获取到的position和下面onscroll方法里的firstVisibleItem值一致
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://手指离开或者惯性滑动停止
                        break;
                }

            }

            /*
            * firstVisibleItem  可见的条目中第一个条目
            * visibleItemCount 一屏幕中可见条目
            * totalItemCount   总条目
            * */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("qcl", "firstVisibleItem   " + firstVisibleItem);
                if (firstVisibleItem > 0) {
                    header_title.setVisibility(View.VISIBLE);
                    if (firstVisibleItem < 5) {
                        header_title.setText(Cheeses.TITLES[0]);
                    } else if (firstVisibleItem >= 5 && firstVisibleItem < 9) {
                        header_title.setText(Cheeses.TITLES[1]);
                    } else {
                        header_title.setText(Cheeses.TITLES[2]);
                    }
                } else {
                    header_title.setVisibility(View.INVISIBLE);

                }
            }
        });
```

通过监听listview的滚动，来做相应的吸顶处理。下面是整个使用完整代码

```java
package com.demosqcl;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*
* 1,状态栏透明
* 2，自定义控件基础学习
*
* */
public class MainActivity extends Activity {
    private ListView mListView;//自定义的listview
    private ImageView mIvHeader;//顶部用来放大的的图片
    private TextView header_title;//顶部用来放大的的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.plv);
        header_title = (TextView) findViewById(R.id.header_title);
        final View headerView = View.inflate(getApplicationContext(), R.layout.list_header, null);
        mIvHeader = (ImageView) headerView.findViewById(R.id.header_iv);
        // 给ListView加头, 要放在setAdapter之前
        mListView.addHeaderView(headerView);


        /*
        * 4.4以后时间栏状态栏透明
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


        final MyAdapter myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);

        /*
        * 添加一个滚动 监听
        * 用来实现标题置顶效果
        * */
        mListView.setOnScrollListener(new ListView.OnScrollListener() {

            //滑动状态监听
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://拖动
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://惯性滑动
                        // 显示滑动时屏幕可见条目中离标题栏最近的第一行
                        int position = mListView.getFirstVisiblePosition();
                        //由于listview添加了一个header，所以position=0代表的是header
                        //这里获取到的position和下面onscroll方法里的firstVisibleItem值一致
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://手指离开或者惯性滑动停止
                        break;
                }

            }

            /*
            * firstVisibleItem  可见的条目中第一个条目
            * visibleItemCount 一屏幕中可见条目
            * totalItemCount   总条目
            * */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("qcl", "firstVisibleItem   " + firstVisibleItem);
                if (firstVisibleItem > 0) {
                    header_title.setVisibility(View.VISIBLE);
                    if (firstVisibleItem < 5) {
                        header_title.setText(Cheeses.TITLES[0]);
                    } else if (firstVisibleItem >= 5 && firstVisibleItem < 9) {
                        header_title.setText(Cheeses.TITLES[1]);
                    } else {
                        header_title.setText(Cheeses.TITLES[2]);
                    }
                } else {
                    header_title.setVisibility(View.INVISIBLE);

                }
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Cheeses.NAMES.length + Cheeses.TITLES.length;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == 4 || position == 8) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public String getItem(int position) {
            String info = "";
            if (position == 0 || position == 4 || position == 8) {
                info = Cheeses.TITLES[position / 4];
            } else if (position < 4) {
                info = Cheeses.NAMES[position - 1];
            } else if (position > 4 && position < 8) {
                info = Cheeses.NAMES[position - 2];
            } else {
                info = Cheeses.NAMES[position - 3];
            }
            return info;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            View view = null;
            if (type == 0) {// 标题
                ViewHolder holder = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    view = View.inflate(getBaseContext(), R.layout.view_item_app_type, null);
                    holder.titile = (TextView) view.findViewById(R.id.type);
                    view.setTag(holder);
                } else {
                    view = convertView;
                    holder = (ViewHolder) view.getTag();
                }
                holder.titile.setText(getItem(position));
                return view;
            } else {
                ViewHolder holder = null;
                // 视图优化 start---
                if (convertView == null) {
                    holder = new ViewHolder();
                    view = View.inflate(getBaseContext(), R.layout.view_item_app_info, null);
                    holder.name = (TextView) view.findViewById(R.id.app_name);
                    view.setTag(holder);
                } else {
                    view = convertView;
                    holder = (ViewHolder) view.getTag();
                }
                holder.name.setText(getItem(position));
                return view;

            }
        }
    }

    class ViewHolder {
        TextView titile;
        TextView name;
    }
}
```

有问题可以加我微信2501902696（备注安卓）
##我的博客
http://blog.csdn.net/qiushi_1990
