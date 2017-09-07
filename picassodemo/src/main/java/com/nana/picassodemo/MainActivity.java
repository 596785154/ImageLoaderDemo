package com.nana.picassodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        //initDefaultSizeData();
        //initReSizeData();
        //initCutData();
    }

    private void initData() {
        /**
         * 加载本地数据库图片的大小，取消于控件设置的大小
         */
        Picasso.with(this).load(imageUrl).into(urlImage);
    }

    private void initLocalData() {
        /**
         * 加载本地资源文件
         */
        Picasso.with(this).load(R.mipmap.moviepicture).into(urlImage);
        //Picasso.with(this).load(new File(...)).into(imageView2);
    }

    private void initDefaultSizeData() {
        /**
         * 根据ImageView大小，显示图片
         */
        Picasso.with(this).load(imageUrl)
                .fit()  //控件不能设置成wrap_content,也就是必须有大小才行,fit()才让图片的宽高等于控件的宽高，设置fit()，不能再调用resize()
                .placeholder(R.mipmap.loading)  //当图片没有加载上的时候，显示的图片
                .error(R.mipmap.load_fail)  //当图片加载错误的时候，显示图片
                .into(urlImage);  //将图片加载到哪个控件中
    }

    private void initReSizeData() {
        /**
         * 通过程序代码，来显示图片大小
         */
        Picasso.with(this).load(imageUrl)
                .resize(200, 150)  //为图片重新定义大小
                .centerCrop()  //图片要填充整个控件，去两边留中间
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .into(urlImage);
    }

    private void initCutData() {
        /**
         * 截取图片
         */
        Picasso.with(this).load(imageUrl)
                .transform(new CropSquareTransformation())  //通过程序截取图片
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .into(urlImage);
    }

}
