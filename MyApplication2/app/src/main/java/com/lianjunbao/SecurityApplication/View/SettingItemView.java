package com.lianjunbao.SecurityApplication.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianjunbao.administrator.securityapplication.R;

public class SettingItemView extends RelativeLayout {

    TextView tv_sync_title;
    private TextView tv_sync_status;
    private CheckBox cb_sync;
    private final String namespace="http://schemas.android.com/apk/res/com.lianjunbao.administrator.securityapplication";
    private String str_title;
    private String str_desc_on;
    private String str_desc_off;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        str_title = attrs.getAttributeValue(namespace,"sync_title");
        str_desc_on = attrs.getAttributeValue(namespace,"sync_desc_on");
        str_desc_off = attrs.getAttributeValue(namespace,"sync_desc_off");
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){

        View.inflate(getContext(), R.layout.view_setting_item,this);

        tv_sync_title = (TextView) findViewById(R.id.tv_sync_title);
        tv_sync_status = (TextView)findViewById(R.id.tv_sync_status);
        cb_sync = (CheckBox)findViewById(R.id.cb_sync);

        setTv_sync_title(str_title);
    }

    public void setTv_sync_title(String value){
        tv_sync_title.setText(value);
    }

    public  void setTv_sync_status(String value){
        tv_sync_status.setText(value);
    }

    public Boolean isChecked(){
        return cb_sync.isChecked();
    }

    public void setCb_sync(Boolean value){
        cb_sync.setChecked(value);
        if(value){
            setTv_sync_status(str_desc_on);
        }else{
            setTv_sync_status(str_desc_off);
        }
    }
}
