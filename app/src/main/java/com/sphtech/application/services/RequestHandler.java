package com.sphtech.application.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sphtech.application.MobileDataUsageConstants;
import com.sphtech.application.MobileDataUsageRestService;
import com.sphtech.application.common.BaseFlyContext;
import com.sphtech.application.common.RequestType;
import com.sphtech.application.common.ResponseStatus;
import com.sphtech.application.common.Utils;
import com.sphtech.application.listener.IResponseReceivedNotifyInterface;
import com.sphtech.application.listener.ResponseArgs;
import com.sphtech.application.model.MobileDataUsageErrorResponse;
import com.sphtech.application.model.MobileDataUsageResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RequestHandler {
    private static final String TAG = RequestHandler.class.getName();

    private static final int TIMEOUT_VALUE = 60000;
    public static RequestHandler requestHandler;

    private Retrofit retrofit;
    private MobileDataUsageRestService service;

    public static Utils utils;

    public RequestHandler() {


        try {
             utils = new Utils(BaseFlyContext.getInstant().getApplicationContext());

           /* OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor( utils.REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addNetworkInterceptor(utils.REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .cache(cache)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(MobileDataUsageConstants.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build();*/


          retrofit =  new Retrofit.Builder()
                    .baseUrl( MobileDataUsageConstants.BASE_URL )
                    .client( provideOkHttpClient() )
                    .addConverterFactory(JacksonConverterFactory.create() )
                    .build();
            service = retrofit.create(MobileDataUsageRestService.class);



        } catch (Exception e) {
            Log.e("RequestHandler except", e + "");
        }
    }
    private  OkHttpClient provideOkHttpClient ()
    {
        return new OkHttpClient.Builder()
                .addInterceptor( utils.provideHttpLoggingInterceptor() )
                .addInterceptor( utils.provideOfflineCacheInterceptor() )
                .addNetworkInterceptor(utils. provideCacheInterceptor() )
                .cache( utils.provideCache() )
                .build();
    }


    public static RequestHandler getRequestHandler() {

        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }


    public void getMobileUsageDataRequest(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String url) {


        Call<MobileDataUsageResponse> call = service.getMobileDataUsage(url);
        call.enqueue(new Callback<MobileDataUsageResponse>() {
            @Override
            public void onResponse(Call<MobileDataUsageResponse> call, Response<MobileDataUsageResponse> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, response.toString());





                    Log.i("formattedData", new Gson().toJson(response.body())+"");

                    Type type = new TypeToken<MobileDataUsageResponse>() {
                    }.getType();
                    iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(response.body(), ResponseStatus.success, RequestType.getMobileDataUsage));


                } else {
                    try {
                        Converter<ResponseBody, MobileDataUsageErrorResponse> errorConverter = retrofit.responseBodyConverter(MobileDataUsageErrorResponse.class, new Annotation[0]);
                        MobileDataUsageErrorResponse error = errorConverter.convert(response.errorBody());
                        //showRetry(error.getError().getMessage());
                        Type type = new TypeToken<MobileDataUsageErrorResponse>() {
                        }.getType();
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(error, ResponseStatus.success, RequestType.errorResponse));

                        Log.e("IOException error:", new Gson().toJson(error.toString()).toString());

                    } catch (Exception e) {
                        Log.e(TAG, "IOException parsing error:", e);
                    }
                }


            }

            @Override
            public void onFailure(Call<MobileDataUsageResponse> call, Throwable t) {
                // Log error here since request failed
                iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest, RequestType.errorResponse));


                Log.e(TAG, t.toString());
            }
        });
    }







/*
    public void getQuoteOfTheDay() {
        Call<QuoteOfTheDayResponse> call = service.getQuoteOfTheDay();

        call.enqueue(new Callback<QuoteOfTheDayResponse>() {

            @Override
            public void onResponse(Call<QuoteOfTheDayResponse> call, Response<QuoteOfTheDayResponse> response) {
                if (response.isSuccessful()) {

                    //textViewQuoteOfTheDay.setText(response.body().getContents().getQuotes().get(0).getQuote());
                    Log.i("Response:", new Gson().toJson(response.toString()));

                } else {
                    try {
                        Converter<ResponseBody, QuoteOfTheDayErrorResponse> errorConverter = retrofit.responseBodyConverter(QuoteOfTheDayErrorResponse.class, new Annotation[0]);
                        QuoteOfTheDayErrorResponse error = errorConverter.convert(response.errorBody());
                        //showRetry(error.getError().getMessage());

                        Log.e("IOException error:", new Gson().toJson(error.toString()).toString());

                    } catch (IOException e) {
                        Log.e(TAG, "IOException parsing error:", e);
                    }

                }
            }

            @Override
            public void onFailure(Call<QuoteOfTheDayResponse> call, Throwable t) {
                //Transport level errors such as no internet etc.
            }
        });


    }*/
}
