package com.sphtech.application.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sphtech.application.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class Utils {
    Context context;
    public static final String CACHE_CONTROL = "Cache-Control";

    public Utils(Context context) {
        this.context = context;
    }




    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static String getYear(String data) {
        String year = "";
        String[] separated = data.split("-");
        year = separated[0];
        return year;
    }

    public static String getQuater(String data) {
        String quaterName = "";
        String[] separated = data.split("-");
        quaterName = separated[1];
        return quaterName;
    }
    public  Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( BaseFlyContext.getInstant().getApplicationContext().getCacheDir(), "http-cache" ),
                    10485760 ); // 10 MB
        }
        catch (Exception e)
        {
           System.out.print(e.toString());
        }
        return cache;
    }

    public  HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(message -> {
                    System.out.print(message.toString());
                });
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? HEADERS : NONE );
        return httpLoggingInterceptor;
    }

    public  Interceptor provideCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public okhttp3.Response intercept (Chain chain) throws IOException
            {
                okhttp3.Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    public  Interceptor provideOfflineCacheInterceptor ()
    {
        return chain -> {
            Request request = chain.request();

            if (isNetworkAvailable() )
            {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale( 7, TimeUnit.DAYS )
                        .build();

                request = request.newBuilder()
                        .cacheControl( cacheControl )
                        .build();
            }

            return chain.proceed( request );
        };
    }

}
