package com.nana.glidedemo;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by HTC on 2017/2/5.
 */
public class GlideConfiguration implements GlideModule {
    //Glide图片默认加载格式是RGB565
    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        Log.d("=====Chunna.zheng=====","执行了将加载的图片换成ARGB格式");
        glideBuilder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
