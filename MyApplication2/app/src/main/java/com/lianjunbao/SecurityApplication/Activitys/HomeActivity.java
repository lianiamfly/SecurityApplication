package com.lianjunbao.SecurityApplication.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianjunbao.administrator.securityapplication.R;
public class HomeActivity extends Activity {

    private GridView gv_Home;

    private String[] mItems = new String[] { "手机防盗", "通讯卫士", "软件管理", "进程管理",
            "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };

    private int[] mPics = new int[] { R.drawable.home_safe,
            R.drawable.home_callmsgsafe, R.drawable.home_apps,
            R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan, R.drawable.home_sysoptimize,
            R.drawable.home_tools, R.drawable.home_settings };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        gv_Home= (GridView) findViewById(R.id.gv_Home);
        gv_Home.setAdapter(new HomeAdapter());
    }

    class HomeAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(HomeActivity.this
            ,R.layout.gridviewitem,null);
            ImageView imageView= (ImageView) view.findViewById(R.id.gvItem_iv);
            TextView textView= (TextView) view.findViewById(R.id.gvItem_tv);
            //对需要添加的view中的图片和文字进行修改
            textView.setText(mItems[position]);
            imageView.setImageResource(mPics[position]);
            return view;
        }
    }
}
