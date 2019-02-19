package com.sphtech.application.services;


import com.sphtech.application.model.MobileDataConsumptionYearlyModel;

import io.reactivex.Observable;


public interface IRemoteServices {

    Observable<MobileDataConsumptionYearlyModel> getMobileDataUsage(String text);

}


