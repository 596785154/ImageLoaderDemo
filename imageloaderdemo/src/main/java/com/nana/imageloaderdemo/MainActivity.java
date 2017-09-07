package com.nana.imageloaderdemo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends Activity {
    String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";
    private DisplayImageOptions options;
    private ImageView urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlImage = (ImageView)findViewById(R.id.urlImage);

        initOptions();
        showPicture();
    }

    private void initOptions(){
        //配置显示参数
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.load_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.load_fail)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .build();
    }

    private void showPicture() {
        /**
         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
         * 参数5  图片下载进度的监听
         */
        ImageLoader.getInstance()
                .displayImage(imageUrl, urlImage, options);
    }
}
