package com.nana.picassodemo;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * picasso的Transformation方法，对图片进行截取
 * Created by HTC on 2017/2/4.
 */
public class CropSquareTransformation implements Transformation{
    //截取原图的1/2宽度和高度作为bitmap的宽度和高度
    @Override
    public Bitmap transform(Bitmap sourceBitmap) {
        int x=sourceBitmap.getWidth()/2;
        int y=sourceBitmap.getHeight()/2;
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap,0,0,x,y);
        if (resultBitmap != sourceBitmap){
            sourceBitmap.recycle();//释放bitmap
        }
        return resultBitmap;
    }

    @Override
    public String key() {
        return "square()";
    }
}
