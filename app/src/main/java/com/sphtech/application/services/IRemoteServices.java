package com.sphtech.application.services;


import com.sphtech.application.model.TotalDataConsumption;

import io.reactivex.Observable;

/**
 * Created by Thet Paing Tun on 2/2/2018.
 */

public interface IRemoteServices {

    Observable<TotalDataConsumption> getMobileDataUsage(String text);

}


