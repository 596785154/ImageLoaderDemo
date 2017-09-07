package com.nana.frescodemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by xpbuild on 2017/2/6.
 */
public class MyAppplication extends Application{
    @Override
    public void onCreate() {

        Fresco.initialize(getApplicationContext());

        super.onCreate();

    }
}
