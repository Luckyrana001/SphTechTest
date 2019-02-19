package com.sphtech.application.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sphtech.application.MobileDataUsageConstants;
import com.sphtech.application.MobileDataUsageRestService;
import com.sphtech.application.common.BaseFlyContext;
import com.sphtech.application.common.RequestType;
import com.sphtech.application.common.ResponseStatus;
import com.sphtech.application.listener.IResponseReceivedNotifyInterface;
import com.sphtech.application.listener.ResponseArgs;
import com.sphtech.application.model.MobileDataUsageErrorResponse;
import com.sphtech.application.model.MobileDataUsageResponse;
import com.sphtech.application.model.Records;
import com.sphtech.application.model.TotalDataConsumption;
import com.squareup.okhttp.Cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.sphtech.application.MobileDataUsageConstants.START_REQUEST;

public class RequestHandler {
    private static final String TAG = RequestHandler.class.getName();

    private static final int TIMEOUT_VALUE = 60000;
    public static RequestHandler requestHandler;
    //protected Gson gson;
    //RequestQueue queue;
    private Retrofit retrofit;
    private MobileDataUsageRestService service;

    private long cacheSize = 5 * 1024 * 1024;
    Cache myCache = new Cache(BaseFlyContext.getInstant().getApplicationContext().getCacheDir(), cacheSize);


    public RequestHandler() {


        try {
           /* Utils utils = new Utils(BaseFlyContext.getInstant().getApplicationContext());

            OkHttpClient client = new OkHttpClient();
            client.networkInterceptors().add(utils.REWRITE_CACHE_CONTROL_INTERCEPTOR);

            //setup cache
            File httpCacheDirectory = new File(BaseFlyContext.getInstant().getApplicationContext().getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

             //add cache to the client
            client.setCache(cache);

*/


            OkHttpClient client = new OkHttpClient();
            // client.interceptors().add(new LoggingInterceptor());
            retrofit = new Retrofit.Builder()
                    .baseUrl(MobileDataUsageConstants.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build();
            service = retrofit.create(MobileDataUsageRestService.class);






           /* queue = Volley.newRequestQueue(BaseFlyContext.getInstant().getApplicationContext());
            //gson = new GsonBuilder().create();
            gson = new GsonBuilder().setPrettyPrinting().create();*/
        } catch (Exception e) {
            Log.e("RequestHandler except", e + "");
        }
    }

    public static RequestHandler getRequestHandler() {

        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }


    public void getMobileUsageDataRequest(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String text) {


        Call<MobileDataUsageResponse> call = service.getMobileDataUsage(START_REQUEST);
        call.enqueue(new Callback<MobileDataUsageResponse>() {
            @Override
            public void onResponse(Call<MobileDataUsageResponse> call, Response<MobileDataUsageResponse> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, response.toString());

                    ArrayList<Records> records = response.body().getResult().getRecords();

                    TotalDataConsumption totalDataConsumption = new TotalDataConsumption();
                    ArrayList<String> quaterData = new ArrayList<>();
                    Double data = 0.0;
                    boolean isThereDataVolumeDecrease = false;
                    for(int i=0;i<records.size();i++){
                        Records rec = records.get(i);
                        if(rec.getQuarter().contains("2018")){
                            data = data + rec.getVolumeOfMobileData();
                            quaterData.add(rec.getVolumeOfMobileData()+"");
                            if(  i>0 && !isThereDataVolumeDecrease){
                                if(rec.getVolumeOfMobileData() < records.get(i-1).getVolumeOfMobileData())
                                {
                                    isThereDataVolumeDecrease =true;
                                }
                            }
                        }
                    }

                    totalDataConsumption.setTotalDataConsumption(data+"");
                    totalDataConsumption.setQuaterDataVolume(quaterData);
                    totalDataConsumption.setThereDecreaseInVolumeOfData(isThereDataVolumeDecrease);


                    Type type = new TypeToken<TotalDataConsumption>() {
                    }.getType();
                    iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(totalDataConsumption, ResponseStatus.success, RequestType.getMobileDataUsage));


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
