package com.nana.httpclientdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";

    private ImageView urlImage;
    private Bitmap urlBitmap;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x123){
                //显示图片
                urlImage.setImageBitmap(urlBitmap);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlImage = (ImageView)findViewById(R.id.urlImage);
        MyThread thread = new MyThread();
        thread.start();
    }

    private class MyThread extends Thread{
        InputStream is;
        @Override
        public void run() {

        }
    }
}
