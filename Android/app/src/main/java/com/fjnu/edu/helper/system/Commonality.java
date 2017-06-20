package com.fjnu.edu.helper.system;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZhouShiqiao on 2017/6/7 0007.
 */

public class Commonality {
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
    public static Bitmap dowmloadBitmap(String str){
        Bitmap bitmap=null;
        InputStream in =null;
        try {
            URL url =new URL(str);
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn=(HttpURLConnection)conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                in=httpConn.getInputStream();
            }
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String[] recommend(ArrayList<String> id, int numbers) {
        String items =null;
        if(id.size()>0) {
            items = id.get(0);
            for (int i = 1; i < id.size(); i++) {
                items = items + "-" + id.get(i);
            }
        }
        try {
            String str = "http://139.199.174.96:5000/predict?item="+items+"&num="+numbers;
            URL url = new URL(str);
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConn.getInputStream();
                String list = readString(in);
                list = list.replace("[", "");
                list = list.replace("]", "");
                String[] sourceStrArray = list.split(", ");
                return sourceStrArray;
            } else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String readString(InputStream in) throws Exception {
        byte[]data = new byte[1024];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while((length=in.read(data))!=-1){
            bout.write(data,0,length);
        }
        return new String(bout.toByteArray(),"UTF-8");

    }
}
