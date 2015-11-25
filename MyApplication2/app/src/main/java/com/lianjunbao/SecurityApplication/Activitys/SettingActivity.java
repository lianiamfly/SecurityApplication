package com.lianjunbao.SecurityApplication.Activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.lianjunbao.SecurityApplication.View.SettingItemView;
import com.lianjunbao.administrator.securityapplication.R;

public class SettingActivity extends Activity{

    private SettingItemView siv;
    private final int SYNC_MODE_OPEN=1;
    private final int SYNC_MODE_CLOSE=2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        siv = (SettingItemView) findViewById(R.id.stv_sync);

        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        Boolean sync_flag= sharedPreferences.getBoolean("auto_update", true);
        if(sync_flag){
            setSync_content(SYNC_MODE_OPEN);
        }else{
            setSync_content(SYNC_MODE_CLOSE);
        }

        siv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siv.isChecked()){
                    setSync_content(SYNC_MODE_CLOSE);
                }else{
                    setSync_content(SYNC_MODE_OPEN);
                }
            }
        });
    }

    private  void setSync_content(int value){
        switch (value){
            case SYNC_MODE_OPEN:
                siv.setCb_sync(true);
                sharedPreferences.edit().putBoolean("auto_update",true).commit();
                break;
            case SYNC_MODE_CLOSE:
                siv.setCb_sync(false);
                sharedPreferences.edit().putBoolean("auto_update",false).commit();
                break;
        }
    }
}
