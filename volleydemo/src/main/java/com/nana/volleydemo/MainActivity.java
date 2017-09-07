package com.nana.volleydemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * Volley实现图片加载的三种方式：
 * 1、imageRequest
 * 2、imageLoaer
 * 3、NetworkImageView
 */
public class MainActivity extends AppCompatActivity {
    String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";
    private ImageView urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlImage = (ImageView) findViewById(R.id.urlImage);

        //initStringRequest();
        //initJsonRequest();
        //initImageRequest();
        initImageLoader();
        initNetworkImageView();
    }

    private void initStringRequest() {
        //1. 创建一个RequestQueue对象。
        RequestQueue mQueue = Volley.newRequestQueue(this);

        //2. 创建一个Request对象。
        StringRequest stringRequest = new StringRequest("https://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("Chunna.zheng", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Chunna.zheng", volleyError.getMessage(), volleyError);
            }
        });

        //3. 将Request对象添加到RequestQueue里面。
        mQueue.add(stringRequest);
    }

    private void initJsonRequest() {
        RequestQueue mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(jsonObjectRequest);
    }

    private void initImageRequest() {
        RequestQueue mQueue = Volley.newRequestQueue(this);

        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        urlImage.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                urlImage.setImageResource(R.mipmap.load_fail);
            }
        });

        mQueue.add(imageRequest);
    }

    private void initImageLoader() {
        /**
         * ImageLoader要比ImageRequest更加高效，它不仅可以帮我们对图片进行缓存，
         * 还可以过滤掉重复的链接，避免重复发送请求。
         */

        //1. 创建一个RequestQueue对象。
        RequestQueue mQueue = Volley.newRequestQueue(this);

        //2. 创建一个ImageLoader对象。
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

        //没有特殊需求可以使用默认的缓存类
        /*ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });*/

        //3. 获取一个ImageListener对象。
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(urlImage,
                R.mipmap.loading, R.mipmap.load_fail);

        //4. 调用ImageLoader的get()方法加载网络上的图片。
        imageLoader.get(imageUrl, listener);
        //imageLoader.get(imageUrl, listener, 200, 200);
    }

    private void initNetworkImageView() {
        /**
         * 1. 创建一个RequestQueue对象。
         * 2. 创建一个ImageLoader对象。
         * 3. 在布局文件中添加一个NetworkImageView控件。
         * 4. 在代码中获取该控件的实例。
         * 5. 设置要加载的图片地址。
         */
        RequestQueue mQueue = Volley.newRequestQueue(this);

        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
        networkImageView.setDefaultImageResId(R.mipmap.loading);
        networkImageView.setErrorImageResId(R.mipmap.load_fail);
        networkImageView.setImageUrl(imageUrl, imageLoader);
    }
}
