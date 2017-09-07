#1. Android应用加载网络图片

##1.1 Android应用获取网络资源注意事项

1. 需要添加访问网络权限

2. 现在Android不允许在主线程中操作网络相关东西,所以和网络操作相关的代码要放在子线程中执行。

##1.2 URL访问网络资源

1.	创建url对象，根据url找到图片地址

2.	打开url对应的资源输入流

3.	从InputStream中解析出图片bitmap

4.	UI组件显示图片

##1.3 URLConnection访问网络资源

借助于URLConnection类应用程序可以非常方便的与指定站点交换信息：包括发送GET请求、POST请求，并获取网站的响应。

1.	创建url对象，根据url找到图片地址

2.	通过url对象openConnection方法创建URLConnection对象

3.	设置URLConnection的参数和普通请求属性

4.	如果只是发送GET方式请求，使用connect方法建立和远程资源之间的实际连接即可，（如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求。）

5.	远程资源变为可用，程序输入流读取远程资源的数据。

6.	从InputStream中解析出图片bitmap

7.	UI组件显示图片

##1.4 HttpURLConnection访问网络资源

HttpURLConnection是URLConnection子类。访问方式和URLConnection一样。

1.	创建url对象，根据url找到图片地址

2.	通过url对象openConnection方法创建HttpURLConnection对象

3.	设置HttpURLConnection的参数和普通请求属性

4.	如果只是发送GET方式请求，使用connect方法建立和远程资源之间的实际连接即可，（如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求。）

5.	远程资源变为可用，程序输入流读取远程资源的数据。

6.	从InputStream中解析出图片bitmap

7.	UI组件显示图片

##1.5 HttpClient访问（未经验证）

HttpClient是增强版HttpURLConnection。可以访问被保护的网站：需要用户登录和用户权限的网站。

1.	创建HttpClient对象

2.	创建HttpGet/HttpPost对象

3.	设置请求参数

4.	调用HttpClient对象的execute(HttpUriRequest request request)发送请求，执行该方法返回一个HttpResponse.

5.	调用HttpResponse的getEntity()方法获取HttpEntity对象。

6.	调用HttpEntity的getContent()方法获取到输入流内容

7.	从InputStream中解析出图片bitmap

8.	UI组件显示图片

##1.6 WebView浏览

WebView本身是一个浏览器实现，内核基于开源WebKit引擎。
注意：有一点问题，就是第一次加载时，如果图片没获取到会出现错误的图片

1.	在布局文件中添加WebView控件

2.	使用WebView自身的loadUrl(String url)方法通过url加载图片

#2. 使用开源库加载图片

##2.1 开源库的优点

1. 使用简单
都可以通过一句代码可实现图片获取和显示。

2. 可配置度高，自适应程度高
图片缓存的下载器（重试机制）、解码器、显示器、处理器、内存缓存、本地缓存、线程池、缓存算法等大都可轻松配置。
自适应程度高，根据系统性能初始化缓存配置、系统信息变更后动态调整策略。比如根据 CPU 核数确定最大并发数，根据可用内存确定内存缓存大小，网络状态变化时调整最大并发数等

3. 多级缓存
都至少有两级缓存、提高图片加载速度。

4. 支持多种数据源
支持多种数据源，网络、本地、资源、Assets 等

5. 支持多种 Displayer
不仅仅支持 ImageView，同时支持其他 View 以及虚拟的 Displayer 概念。

6. 其他小共同点
包括支持动画、支持 transform 处理、获取 EXIF 信息等。

##2.2 ImageLoader 

###2.2.1 设计及流程

整个库分为 ImageLoaderEngine，Cache 及 ImageDownloader，ImageDecoder，BitmapDisplayer，BitmapProcessor 五大模块，其中 Cache 分为 MemoryCache 和 DiskCache 两部分。
ImageLoader 收到加载及显示图片的任务，并将它交给 ImageLoaderEngine，ImageLoaderEngine 分发任务到具体线程池去执行，任务通过 Cache 及 ImageDownloader 获取图片，中间可能经过 BitmapProcessor 和 ImageDecoder 处理，最终转换为Bitmap 交给 BitmapDisplayer 在 ImageAware 中显示。
![这里写图片描述](http://img.blog.csdn.net/20170907195454805?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemNuNTk2Nzg1MTU0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
###2.2.2 功能特性

1. 多线程异步加载和显示图片（图片来源于网络、sd卡、assets文件夹，drawable文件夹（不能加载9patch），新增加载视频缩略图）

2. 支持通过“listener”监视加载的过程，可以暂停加载图片，在经常使用的ListView、GridView中，可以设置滑动时暂停加载，停止滑动时加载图片（便于节约流量，在一些优化中可以使用）

3. 缓存图片至内存时，可以更加高效的工作

4. 高度可定制化（可以根据自己的需求进行各种配置，如：线程池，图片下载器，内存缓存策略等）

5. 支持图片的内存缓存，SD卡（文件）缓存

6. 在网络速度较慢时，还可以对图片进行加载并设置下载监听

###2.2.3 使用步骤

1. 添加jar包

2. Manifest.xml文件中添加网络和读写SD卡权限

3. 在Application中配置ImageLoaderConfiguration参数，配置好ImageLoaderConfiguration，一定不要忘记进行初始化操作

4. 首先要得到ImageLoader的实例(使用的单例模式)

5. 相关显示参数配置

6. 显示图片

##2.3 Picasso 

###2.3.1 设计及流程

整个库分为 Dispatcher，RequestHandler 及 Downloader，PicassoDrawable 等模块。
Picasso 收到加载及显示图片的任务，创建 Request 并将它交给 Dispatcher，Dispatcher 分发任务到具体 RequestHandler，任务通过 MemoryCache 及 Handler(数据获取接口) 获取图片，图片获取成功后通过 PicassoDrawable 显示到 Target 中。
![这里写图片描述](http://img.blog.csdn.net/20170907195522228?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemNuNTk2Nzg1MTU0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
###2.3.2 功能特性

1.自带统计监控功能
支持图片缓存使用的监控，包括缓存命中率、已使用内存大小、节省的流量等。

2.支持优先级处理
每次任务调度前会选择优先级高的任务，比如 App 页面中 Banner 的优先级高于 Icon 时就很适用。

3.支持延迟到图片尺寸计算完成加载

4.支持飞行模式、并发线程数根据网络类型而变

手机切换到飞行模式或网络类型变换时会自动调整线程池最大并发数，比如 wifi 最大并发为 4， 4g 为 3，3g 为 2。（Picasso 根据网络类型来决定最大并发数，而不是 CPU 核数。）

5.无”本地缓存
“无”本地缓存，不是说没有本地缓存，而是 Picasso 自己没有实现，交给了 Square 的另外一个网络库 okhttp 去实现，这样的好处是可以通过请求 Response Header 中的 Cache-Control 及 Expired 控制图片的过期时间。

###2.3.3 使用步骤

1. 添加jar包

2. Manifest.xml文件中添加网络和读写SD卡权限

3. 配置并显示图片

##2.4 Glide

###2.4.1 设计及流程
整个库分为 RequestManager（请求管理器），Engine（数据获取引擎）、Fetcher（数据获取器）、MemoryCache（内存缓存）、DiskLRUCache、Transformation（图片处理）、Encoder（本地缓存存储）、Registry（图片类型及解析器配置）、Target（目标）等模块。

Glide 收到加载及显示资源的任务，创建 Request 并将它交给RequestManager，Request 启动 Engine 去数据源获取资源(通过 Fetcher )，获取到后 Transformation 处理后交给 Target。

Glide 依赖于 DiskLRUCache、GifDecoder 等开源库去完成本地缓存和 Gif 图片解码工作。
![这里写图片描述](http://img.blog.csdn.net/20170907195549045?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemNuNTk2Nzg1MTU0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
###2.4.2 功能特性

1.图片缓存->媒体缓存
Glide 不仅是一个图片缓存，它支持 Gif、WebP、缩略图。甚至是 Video，所以更该当做一个媒体缓存。

2.支持优先级处理

3.与 Activity/Fragment 生命周期一致，支持 trimMemory
Glide 对每个 context 都保持一个 RequestManager，通过 FragmentTransaction 保持与 Activity/Fragment 生命周期一致，并且有对应的 trimMemory 接口实现可供调用。

4.支持 okhttp、Volley 
Glide 默认通过 UrlConnection 获取数据，可以配合 okhttp 或是 Volley 使用。实际 ImageLoader、Picasso 也都支持 okhttp、Volley。

5.内存友好
（1）Glide 的内存缓存有个 active 的设计
从内存缓存中取数据时，不像一般的实现用 get，而是用 remove，再将这个缓存数据放到一个 value 为软引用的 activeResources map 中，并计数引用数，在图片加载完成后进行判断，如果引用计数为空则回收掉。

（2）内存缓存更小图片
Glide 以 url、viewwidth、viewheight、屏幕的分辨率等做为联合 key，将处理后的图片缓存在内存缓存中，而不是原始图片以节省大小

（3）与 Activity/Fragment 生命周期一致，支持 trimMemory

（4）图片默认使用默认 RGB565 而不是 ARGB888
虽然清晰度差些，但图片更小，也可配置到 ARGB_888。

（5）其他：Glide 可以通过 signature 或不使用本地缓存支持 url 过期

###2.4.3 使用步骤

1. 添加jar包

2. Manifest.xml文件中添加网络和读写SD卡权限

3. 配置并显示图片

##2.5 Fresco

###2.5.1 设计及流程
核心类：
DraweeView(子类：SimpleDraweeView),DraweeHierarchy(子类：GenericDraweeHierarchy),DraweeController。(类似MVC)

DraweeView：
子类也是SimpleDraweeView，用于显示在屏幕上的视图，相当于V。

DraweeHierarchy：
子类是GenericDraweeHierarchy，主要用于维护和绘制Drawable对象，以及怎样展示等等。相当于M。

DraweeController：
控制器，主要和ImageLoader交互，比如说为图片设置uri，能否在失败时重新加载等等。相当于C。

Fresco的Image Pipeline
![这里写图片描述](http://img.blog.csdn.net/20170907195620242?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemNuNTk2Nzg1MTU0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
###2.5.2 功能特性
1.内存管理
    在5.0以下系统，Fresco将图片放到一个特别的内存区域。在图片不显示的时候，占用的内存会自动被释放。这会使得APP更加流畅，减少因图片内存占用而引发的OOM。
    
2.图片加载
Fresco的Image Pipeline允许用户用很多种方式来自定义图片加载过程
•	为同一个图片指定不同的远程路径，或者使用已经存在本地缓存中的图片
•	先显示一个低清晰度的图片，等高清图下载完之后再显示高清图
•	加载完成回调通知
•	对于本地图，如有EXIF缩略图，在大图加载完成之前，可先显示缩略图
•	缩放或者旋转图片
•	对已下载的图片再次处理
•	支持WebP解码，即使在早先对WebP支持不完善的Android系统上也能正常使用！

3.图片绘制
    Fresco 的 Drawees 设计，带来一些有用的特性：
•	自定义居中焦点
•	圆角图，当然圆圈也行
•	下载失败之后，点击重现下载
•	自定义占位图，自定义overlay, 或者进度条
•	指定用户按压时的overlay

4.图片的渐进式呈现
    渐进式图片格式先呈现大致的图片轮廓，然后随着图片下载的继续，呈现逐渐清晰的图片，这对于移动设备，尤其是慢网络有极大的利好，可带来更好的用户体验。
    
5.动图加载
    Fresco 支持 GIF 和 WebP 格式的动画图片。
    
###2.5.3 使用步骤

1.Manifest.xml文件中添加网络和读写SD卡权限

2.导入
```
dependencies {  
	compile 'com.facebook.fresco:fresco:0.8.0+'//添加的版本可根据官网上的版本
}  
```  
3.在project的build.gradle文件中添加①代码：
```
allprojects {
    repositories {
        jcenter()
        mavenCentral()//①
    }
}
```
4.在你的application类onCreate方法中添加并在manifest.xml文件中添加Application的name属性等于当前application类名
```
Fresco.initialize(instance);
```
5.在xml中引入SimpleDraweeView
```
 <com.facebook.drawee.view.SimpleDraweeView  
	 android:id="@+id/image_view"  
	 android:layout_width="100dp"  
	 android:layout_height="150dp"  
	 fresco:placeholderImage="@drawable/my_drawable"  
/> 
```
注意：在SimpleDraweeView的父控件或者根节点中配置
```
xmlns:fresco="http://schemas.android.com/apk/res-auto";
```
6.在Java代码中开始加载图片
```
 Uri uri = Uri.parse("http://content.52pk.com/files/100623/2230_102437_1_lit.jpg");  
 SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);  
 draweeView.setImageURI(uri);
```

##2.6 Volley 

###2.6.1 设计及流程

主要是通过两种Diapatch Thread不断从RequestQueue中取出请求，根据是否已缓存调用Cache或Network这两类数据获取接口之一，从内存缓存或是服务器取得请求的数据，然后交由ResponseDelivery去做结果分发及回调处理。
![这里写图片描述](http://img.blog.csdn.net/20170907195646893?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemNuNTk2Nzg1MTU0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
主线程中调用RequestQueue的add()方法来添加一条网络请求，这条请求会先被加入到缓存队列当中，如果发现可以找到相应的缓存结果就直接读取缓存并解析，然后回调给主线程。如果在缓存中没有找到结果，则将这条请求加入到网络请求队列中，然后处理发送HTTP请求，解析响应结果，写入缓存，并回调主线程。
![这里写图片描述](http://img.blog.csdn.net/20170907195658535?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemNuNTk2Nzg1MTU0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
###2.6.2 功能特性

1. 通信更快，更简单，更健壮

2. Get、Post网络请求及网络图像的高效率异步处理请求

3. 对网络请求进行排序优先级处理

4. 网络请求的缓存

5. 多级别取消请求（同时取消正在进行的多个网络请求）

6. 和Activity生命周期的联动（当Activity销毁的时候可以同时取消正在进行的网络请求操作，提高性能）

###2.6.3 使用步骤

使用imageRequest加载图片：
1）创建一个RequestQueue对象。
2）创建一个Request对象。
3）将Request对象添加到RequestQueue里面。

使用imageLoader加载图片：
1）创建一个RequestQueue对象。
2）创建一个ImageLoader对象。
3）获取一个ImageListener对象。
4）调用ImageLoader的get()方法加载网络上的图片。

使用NetworkImageView加载图片：
1）创建一个RequestQueue对象。
2）创建一个ImageLoader对象。
3）在布局文件中添加一个NetworkImageView控件。
4）在代码中获取该控件的实例。
5）设置要加载的图片地址

##2.7 几种开源库的区别

1. fresco是重量级库，适合大型应用不适合小应用。ImageLoader、Glide、picasso是轻量级图片框架适合小型应用。Volley适合网络通信操作频繁但数据量小的操作，不适合大数据量的网络操作。

2. glide和picasso使用大致相同，但glide可以加载动态图但picasso做不到，glide可以接收Activity/Fragment作为with的参数用以将生命周期和activity/fragment生命周期保持一致而picasso不能。

3. Volley不能加载本地图片，其他几种框架均可以。并且不支持上传和下载。

4. Volley可以定制自己的Request
