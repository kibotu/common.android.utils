package com.common.android.utils.network;


import android.support.annotation.NonNull;

import com.common.android.utils.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nyaruhodo on 06.03.2016.
 */
public class GeoRequestProvider {

    private static final boolean ENABLE_MOCKING = false;

    public static boolean LOGGING_ENABLED = BuildConfig.DEBUG;

    public static boolean isLoggingEnabled() {
        return LOGGING_ENABLED;
    }

    public static void setLoggingEnabled(final boolean loggingEnabled) {
        LOGGING_ENABLED = loggingEnabled;
    }

    @NonNull
    public static OkHttpClient createHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(LOGGING_ENABLED
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    /**
     * http://freegeoip.net/json/{$ip} //10k requests per hour
     */
    @NonNull
    private static FreeGeoIpService freeGeoIpService() {

        final OkHttpClient client = createHttpLoggingInterceptor();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://freegeoip.net")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FreeGeoIpService.class);
    }

    /**
     * http://ipinfo.io/{$ip}/json //1k per day
     */
    @NonNull
    public static IpInfoIoService ipInfoIoService() {

        final OkHttpClient client = createHttpLoggingInterceptor();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ipinfo.io")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IpInfoIoService.class);
    }

    /**
     * http://ip-api.com/json/{$ip}?fields=country,city,regionName,status //150 per minute
     */
    @NonNull
    public static IpInfoIoService ipApiComService() {

        final OkHttpClient client = createHttpLoggingInterceptor();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ip-api.com")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IpInfoIoService.class);
    }
}
