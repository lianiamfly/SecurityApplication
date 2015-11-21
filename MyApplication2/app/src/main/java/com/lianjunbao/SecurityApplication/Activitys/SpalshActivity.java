package com.lianjunbao.SecurityApplication.Activitys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lianjunbao.administrator.securityapplication.R;

public class SpalshActivity extends AppCompatActivity {

    private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        tv_version=(TextView)findViewById(R.id.tv);
        tv_version.setText("版本号："+getVersionTest());
    }

    private String getVersionTest() {
        String value="";
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            value=packageInfo.versionName;
            System.out.println(value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return value;
    }

}
