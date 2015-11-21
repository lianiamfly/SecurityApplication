package com.lianjunbao.SecurityApplication.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lianjunbao.SecurityApplication.Utills.StreamUtils;
import com.lianjunbao.administrator.securityapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SpalshActivity extends AppCompatActivity {

    private final  int CODE_UPDATE_DIALOG=0;
    private final  int CODE_URL_ERROR=1;
    private final  int CODE_NET_ERROR=2;
    private final  int CODE_JSON_ERROR=3;

    private TextView tv_version;
    private String mVersionName;
    private int mVersionCode;
    private String mDescription;
    private String mDowaloadUrl;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SpalshActivity.this,"URL路径有错误！",Toast.LENGTH_SHORT);
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SpalshActivity.this, "网络连接错误！", Toast.LENGTH_SHORT);
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SpalshActivity.this, "数据解析错误！", Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        tv_version=(TextView)findViewById(R.id.tv);
        tv_version.setText("版本号："+getVersionTest());
        checkUpdate();
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

    private int getCurrentVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void checkUpdate(){
        new Thread(){
            @Override
            public void run() {
                Message mes=Message.obtain();//定义内部参数，用于发送消息实现子线程更新主线程UI
                HttpURLConnection httpURLConnection=null;//网络连接对象，方便使用，方便断开连接
                try {
                    URL url=new URL("http://192.168.1.37/update.json");
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.connect();
                    int responsecode=httpURLConnection.getResponseCode();
                    if(responsecode==200){
                        InputStream is=httpURLConnection.getInputStream();
                        String result= StreamUtils.readFromStream(is);
                        System.out.println("网络返回: "+result);
                        //解析获取的json数据
                        try {
                            JSONObject jsonObject=new JSONObject(result);
                            mVersionName = jsonObject.getString("versionName");
                            mVersionCode = jsonObject.getInt("versionCode");
                            mDescription = jsonObject.getString("description");
                            mDowaloadUrl = jsonObject.getString("downloadUrl");
                            if(mVersionCode>getCurrentVersionCode()){
                                //服务器的apk版本高于当前软件的版本，需要更新
                                mes.what=CODE_UPDATE_DIALOG;
                            }
                        } catch (JSONException e) {
                            mes.what=CODE_JSON_ERROR;
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    mes.what=CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    mes.what=CODE_NET_ERROR;
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(mes);
                    if(httpURLConnection!=null)
                        httpURLConnection.disconnect();//关闭网络连接
                }
            }
        }.start();
    }

    private void showUpdateDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("有新版本可更新");
        builder.setMessage("最新版本号是:" + mVersionName + "\n当前版本号是:" + getVersionTest()
                + "\n" + mDescription);
        builder.setPositiveButton("现在更新!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("更新现在的软件");
            }
        });
        builder.setNegativeButton("以后再说", null);
        builder.show();
    }
}
