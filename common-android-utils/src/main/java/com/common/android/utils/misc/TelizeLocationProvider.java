package com.common.android.utils.misc;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.common.android.utils.interfaces.ICallback;
import com.common.android.utils.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import static com.common.android.utils.misc.RequestQueueProvider.getRequestQueue;

/**
 * Created by Jan Rabe on 26/10/15.
 */
public class TelizeLocationProvider {

    private static String TAG = TelizeLocationProvider.class.getSimpleName();
    public static final String TELIZE_URL = "http://www.telize.com/geoip";

    private TelizeLocationProvider() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void getLocation(@NotNull final ICallback<TelizeModel> callback) {

        getRequestQueue().add(new StringRequest(TELIZE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                callback.onSuccess(new Gson().fromJson(json, TelizeModel.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logger.v(TAG, "" + volleyError.getLocalizedMessage());
            }
        }));
    }

    public static class TelizeModel {

        @SerializedName("dma_code")
        public String dmaCode;
        @SerializedName("ip")
        public String ip;
        @SerializedName("asn")
        public String asn;
        @SerializedName("latitude")
        public double latitude;
        @SerializedName("country_code")
        public String countryCode;
        @SerializedName("offset")
        public String offset;
        @SerializedName("country")
        public String country;
        @SerializedName("isp")
        public String isp;
        @SerializedName("timezone")
        public String timezone;
        @SerializedName("area_code")
        public String areaCode;
        @SerializedName("continent_code")
        public String continentCode;
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("country_code3")
        public String countryCode3;

        @SerializedName("region")
        public String region;
        @SerializedName("city")
        public String city;
        @SerializedName("postal_code")
        public String postalCode;

        @NotNull
        @Override
        public String toString() {
            return "TelizeModel{" +
                    "dmaCode='" + dmaCode + '\'' +
                    ", ip='" + ip + '\'' +
                    ", asn='" + asn + '\'' +
                    ", latitude=" + latitude +
                    ", countryCode='" + countryCode + '\'' +
                    ", offset='" + offset + '\'' +
                    ", country='" + country + '\'' +
                    ", isp='" + isp + '\'' +
                    ", timezone='" + timezone + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", continentCode='" + continentCode + '\'' +
                    ", longitude=" + longitude +
                    ", countryCode3='" + countryCode3 + '\'' +
                    ", region='" + region + '\'' +
                    ", city='" + city + '\'' +
                    ", postalCode='" + postalCode + '\'' +
                    '}';
        }
    }
}
