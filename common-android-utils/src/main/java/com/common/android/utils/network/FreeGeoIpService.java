package com.common.android.utils.network;

import android.support.annotation.NonNull;
import com.common.android.utils.network.model.FreeGeoIpResponseModel;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.WaspRequest;
import com.orhanobut.wasp.http.GET;

/**
 * Created by Nyaruhodo on 06.03.2016.
 */
public interface FreeGeoIpService {

    @NonNull
    @GET("/json")
    WaspRequest getGeoIp(Callback<FreeGeoIpResponseModel> response);
}
