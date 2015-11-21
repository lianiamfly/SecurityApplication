package com.lianjunbao.SecurityApplication.Utills;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/11/21.
 * 读取流的工具
 */
public class StreamUtils {
    public  static  String readFromStream(InputStream inputStream){
        String value="";
        //将输入流读取成String返回
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        int len=0;
        byte[] buffer=new byte[1024];
        try {
            while((len=inputStream.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            value=out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  value;
    }
}
