package com.example.mina.githubrepos.network;


//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import com.example.mina.githubrepos.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mina on 6/1/2017.
 */

public class RetrofitSingleton {


    private static Retrofit mInstance;


    public static synchronized Retrofit getInstance() {

        OkHttpClient.Builder okHttpClientBuilder;
        HttpLoggingInterceptor loggingInterceptor;

        if (mInstance == null) {


            okHttpClientBuilder = new OkHttpClient.Builder();  /// I must use OkHttpClient.Builder to add the log interceptor to the request
            loggingInterceptor = new HttpLoggingInterceptor(); /// I must use HttpLoggingInterceptor to could identify log configuration
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); /// add the log level body, header or ... etc

            if (BuildConfig.DEBUG) {  // only enable log in depug mode to still secure my requests like password ..
                okHttpClientBuilder.addInterceptor(loggingInterceptor);
            }


            /*
            * baseUrl ()— Sets the API base url
            * addConverterFactory() — converter factory for serialization and deserialization of objects
            * addCallAdapterFactory() — Adapter factory for supporting service method return types,
              add instance of RxJava2CallAdapterFactory for Rxjava 2 support.*/
            mInstance = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // <- add this retrofit adapter for Rx
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) /// way to implement Rx.. directly
                    .client(okHttpClientBuilder.build()) /// normal way using interfaces..
                    .build();
        }
        return mInstance;
    }
}
