package com.sphtech.application.services;


import com.sphtech.application.model.MobileDataUsageResponse;

import io.reactivex.Observable;


public interface IRemoteServices {

    //Observable<MobileDataConsumptionYearlyModel> getMobileDataUsage(String text);

    Observable<MobileDataUsageResponse> getMobileDataUsage(String url);

}


