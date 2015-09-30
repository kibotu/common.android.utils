package common.android.utils.network.okhttp;

import android.util.Log;
import com.squareup.okhttp.*;
import common.android.utils.network.RequestExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

/**
 * Created by conrad on 2015/03/09.
 */
public class OKHTTPRequestExecutor extends RequestExecutor {

    private OkHttpClient mOkHttpClient;
    private CookieManager mCookieManager;

    public OKHTTPRequestExecutor() {
        mOkHttpClient = new OkHttpClient();
        mCookieManager = new CookieManager();
        mOkHttpClient.setCookieHandler(mCookieManager);
    }

    @Override
    public void cleanCookies() {
        mCookieManager.getCookieStore().removeAll();
    }

    public <T> void executeRequest(@NotNull final common.android.utils.network.Request<T> request, @NotNull final RequestExecutorListener<T> listener) {
        final Request.Builder builder = new Request.Builder();

        // Set url
        builder.url(request.getRequestURL());

        // Set header fields
        for (final Map.Entry<String, String> entry : request.getHeaderFields().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        // Set HTTP Method
        switch (request.getHTTPMethod()) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(RequestBody.create(MediaType.parse(request.getContentType()), request.getBody()));
                break;
            case PUT:
                builder.put(RequestBody.create(MediaType.parse(request.getContentType()), request.getBody()));
                break;
            case DELETE:
                builder.delete();
                break;
            default:
                break;
        }

        // build request
        final Request httpRequest = builder.build();

        mOkHttpClient.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, @NotNull final IOException e) {
                Log.d("res", e.getLocalizedMessage());
                listener.onError(e);
            }

            @Override
            public void onResponse(@NotNull final Response response) throws IOException {
                try {
                    final List<HttpCookie> cookie = mCookieManager.getCookieStore().getCookies();
                    Log.d("COOKIE", cookie.toString());
                    listener.onSuccess(request.parseResponse(response.body().byteStream()));
                } catch (@NotNull final Exception e) {
                    listener.onError(e);
                }
            }
        });

    }

    @Override
    public <T> void cancelRequest(final common.android.utils.network.Request<T> request) {
        // Do nothing
    }
}
