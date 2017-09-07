package com.nana.glidedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {
    String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";
    private ImageView urlImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlImage = (ImageView)findViewById(R.id.urlImage);

        initData();
        //initLocalData();
        //initAllSizeData();
    }

    private void initData() {
        /** 加载图片
         * 虽然和Picasso两者看起来一样，但是Glide更易用，因为Glide的with方法不光接受Context，
         * 还接受Activity 和 Fragment，Context会自动的从他们获取。
         * 将Activity/Fragment作为with()参数的好处是：图片加载会和Activity/Fragment的生命周期保持一致，
         * 比如Paused状态在暂停加载，在Resumed的时候又自动重新加载。
         */
        Glide.with(this).load(imageUrl)
                .override(300, 200)  //相当于Picasso的resize()
                .placeholder(R.mipmap.loading) //当图片没有加载上的时候，显示的图片
                .error(R.mipmap.load_fail) //当图片加载错误的时候，显示图片
                .into(urlImage);
    }

    private void initLocalData() {
        /**
         * 加载本地资源文件(动态图)
         */
        Glide.with(this).load(R.mipmap.moviepicture).into(urlImage);
        //Picasso.with(this).load(new File(...)).into(imageView2);
    }

    private void initAllSizeData() {
        /**
         *glide默认只缓存和imageView尺寸相同的图片，如果想让glide即缓存全尺寸又缓存其他的
         * 则需要调用diskCacheStrategy方法
         */
        Glide.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(urlImage);
    }
}
