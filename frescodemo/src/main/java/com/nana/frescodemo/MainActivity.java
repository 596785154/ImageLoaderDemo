package com.nana.frescodemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {
    Uri imageUrl = Uri.parse("http://content.52pk.com/files/100623/2230_102437_1_lit.jpg");
    Uri movieUrl = Uri.parse("res://com.nana.frescodemo/" + R.mipmap.moviepicture);
    SimpleDraweeView draweeView;
    SimpleDraweeView circleImageDraweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        draweeView = (SimpleDraweeView) findViewById(R.id.urlImage);
        circleImageDraweeView = (SimpleDraweeView) findViewById(R.id.circleImageDraweeView);

        initData();
        //initPlaceholderImageData();
        //initFailureImageData();
        //initRetryImageData();
        initCircleImageData();
        //initMovePicture();

        /**
         * 还有差不多的ProgressBarImage、BackGroundImage、OverlayImage。。。
         * 实例网址：http://www.cnblogs.com/RGogoing/p/6208171.html
         */

    }

    private void initData() {
        /**
         * 最简单的使用
         */
        draweeView.setImageURI(imageUrl);
    }

    private void initPlaceholderImageData() {
        /** MVC模型
         * DraweeView：子类也是SimpleDraweeView，用于显示在屏幕上的视图，相当于V。
         * DraweeHierarchy：子类是GenericDraweeHierarchy，主要用于维护和绘制Drawable对象，以及怎样展示等等。相当于M。
         * DraweeController：控制器，主要和ImageLoader交互，比如说为图片设置uri，能否在失败时重新加载等等。相当于C。
         */
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(3000).build();//设置淡入淡出效果的显示时间
        DraweeController placeHolderDraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(imageUrl) //为图片设置url
                .setTapToRetryEnabled(true) //设置在加载失败后,能否重试
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(placeHolderDraweeController);
        draweeView.setHierarchy(hierarchy);
    }

    private void initFailureImageData() {

        /**
         * 任意加载一个url,url是不存在的.因此显示加载失败的图片.
         * */
        DraweeController failureImageDraweeController = Fresco.newDraweeControllerBuilder()
                .setUri("ssss")
                .setTapToRetryEnabled(false) //同时设置不可重试.
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(failureImageDraweeController);
    }

    private void initRetryImageData() {
        /**
         * 任意加载一个url,显示重新加载时图片的点击,加载失败的时候,Image pipeline会重试四次,
         * 如果还是加载不出图像,那么显示加载失败图片.
         * */
        DraweeController retryImageDraweeController = Fresco.newDraweeControllerBuilder()
                .setUri("aaaa")
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(retryImageDraweeController);
    }

    private void initCircleImageData() {
        circleImageDraweeView.setImageURI(imageUrl);
    }

    private void initMovePicture() {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(movieUrl)
                .setAutoPlayAnimations(true)
                .build();
        draweeView.setController(controller);
    }

}
