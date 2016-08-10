package com.common.android.utils.network;

import android.support.annotation.NonNull;

import com.common.android.utils.network.model.FreeGeoIpResponseModel;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Nyaruhodo on 06.03.2016.
 */
public interface FreeGeoIpService {

    @NonNull
    @GET("/json")
    Observable<FreeGeoIpResponseModel> getGeoIp();
}