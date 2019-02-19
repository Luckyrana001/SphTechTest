package com.sphtech.application;

import com.sphtech.application.model.MobileDataUsageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MobileDataUsageRestService {

    @GET
    Call<MobileDataUsageResponse> getMobileDataUsage(@Url String url);

    @GET("api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5")
    Call<MobileDataUsageResponse> getMobileDataUsage();


    @GET("datastore_search")
    Call<MobileDataUsageResponse> getMobileDataUsage(
            @Query("resource_id") String resourceId,
            @Query("limit") String limit

    );

}
