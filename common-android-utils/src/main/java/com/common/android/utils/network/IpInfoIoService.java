package com.common.android.utils.network;

import android.support.annotation.NonNull;

import com.common.android.utils.network.model.IpInfoResponseModel;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Nyaruhodo on 06.03.2016.
 */
public interface IpInfoIoService {

    @NonNull
    @GET("/json")
    Observable<IpInfoResponseModel> getGeoIp();
}
