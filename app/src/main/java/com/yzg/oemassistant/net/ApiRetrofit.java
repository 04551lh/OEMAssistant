package com.yzg.oemassistant.net;


import android.text.TextUtils;

import com.yzg.oemassistant.Utils.LogUtils;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzg.oemassistant.constant.BuildConfig;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yzg.oemassistant.constant.ApiConstant.USB_BASE_URL;
import static com.yzg.oemassistant.constant.ApiConstant.WIFI_BASE_URL;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.net
 * @ClassName: ApiRetrofit
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/20 2:36 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/20 2:36 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ApiRetrofit {

    private static final int DEFAULT_CONNECT_TIME = 10;
    private static final int DEFAULT_WRITE_TIME = 30;
    private static final int DEFAULT_READ_TIME = 30;
    private static volatile ApiRetrofit instance;
    private ApiService apiService;
    private BackstageService backstageService;
    private Retrofit apiRetrofit, retrofit;
    private OkHttpClient okHttpClient;
    private String mHttpsURL;

    private ApiRetrofit() {
    }

    public static ApiRetrofit getInstance() {
        LogUtils.setClassName("ApiRetrofit");
        if (instance == null) {
            synchronized (ApiRetrofit.class) {
                if (instance == null) {
                    instance = new ApiRetrofit();
                }
            }
        }
        return ApiRetrofit.instance;
    }

    /**
     * 设置Header
     *
     * @return 拦截器
     */
    private Interceptor getHeaderInterceptor() {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            //添加Token
//                        .header("token", "");

            Request request = requestBuilder.build();
            if(!TextUtils.isEmpty(mHttpsURL)){
                RequestBody requestBody = RequestBody.create(MediaType.parse(ApiConstant.MEDIA_TYPE), "");
                request = new Request.Builder()
                        .url(mHttpsURL)
                        .post(requestBody)
                        .build();
            }
            return chain.proceed(request);
        };
    }

    /**
     * 设置拦截器 打印日志
     *
     * @return 拦截日志打印
     */
    private Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(LogUtils::i);
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            //如果为DEBUG 就打印日志
            if (BuildConfig.DEBUG) {
                okHttpClient = new OkHttpClient().newBuilder()
                        //设置Header
                        .addInterceptor(getHeaderInterceptor())
                        //设置拦截器
                        .addInterceptor(getInterceptor())
                        .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)//连接超时时间
                        .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
                        .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)//设置读操作超时时间
                        .build();
            } else {
                okHttpClient = new OkHttpClient().newBuilder()
                        //设置Header
                        .addInterceptor(getHeaderInterceptor())
                        .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)//连接超时时间
                        .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
                        .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)//设置读操作超时时间
                        .build();
            }

        }

        return okHttpClient;
    }

    public ApiService getApi() {
        //初始化一个client,不然retrofit会自己默认添加一个
        String url = "http:" + USB_BASE_URL;
        if (apiRetrofit == null) {
            apiRetrofit = new Retrofit.Builder()
                    //设置网络请求的Url地址
                    .baseUrl(url)
                    //设置数据解析器
                    .addConverterFactory(GsonConverterFactory.create())
                    //设置网络请求适配器，使其支持RxJava与RxAndroid
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        //创建—— 网络请求接口—— 实例
        if (apiService == null) {
            apiService = apiRetrofit.create(ApiService.class);
        }
        return apiService;
    }

//    private final static String test = "https://test.cloud.background.adasplus.com";
    private final static String test = "https://test.cloud.background.adasplus.com";

    public BackstageService getDeviceApi() {
        //初始化一个client,不然retrofit会自己默认添加一个
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    //设置网络请求的Url地址
                    .baseUrl(test)
                    //设置数据解析器
                    .addConverterFactory(GsonConverterFactory.create())
                    //设置网络请求适配器，使其支持RxJava与RxAndroid
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        //创建—— 网络请求接口—— 实例
        if (backstageService == null) {
            backstageService = retrofit.create(BackstageService.class);
        }
        return backstageService;
    }

    public String getParamWithString(String url,Map<String,String> params) {
        LogUtils.i("params:"+params.size());
        if (params == null || params.size() < 1)
            return url;
        StringBuilder sb = new StringBuilder();
        if (url.indexOf("http://") == 0
                || url.indexOf("https://") == 0) {
            sb.append(url + "?");
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue())
                    .append("&");
        }

        return sb.toString().substring(0, (sb.toString().length() - 1));
    }

    public void setHttpsURL(String mHttpsURL) {
        this.mHttpsURL = mHttpsURL;
    }
}