package common.android.utils.network.asynchttp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import common.android.utils.localization.Localization;
import common.android.utils.network.Request;
import common.android.utils.network.RequestExecutor;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by conrad on 2015/03/17.
 */
public class AsyncHTTPRequestExecutor extends RequestExecutor {

    private static List<String> knownErrorList;

    static {
        knownErrorList = new ArrayList<>();
        knownErrorList.add("API_ERROR_NOTLOGGEDIN");
        knownErrorList.add("error.checkphonenumber.novalid_prefix");
        knownErrorList.add("error.checkphonenumber.novalid_length");
        knownErrorList.add("error.checkphonenumber.dedicated_phonenumber");
        knownErrorList.add("error.makecall.notenoughmoney");
        knownErrorList.add("Not Logged in");
        knownErrorList.add("50");
    }

    @NotNull
    private final PersistentCookieStore mCookieStore;
    AsyncHttpClient mAsyncHttpClient;

    public AsyncHTTPRequestExecutor(@NotNull final Context context) {
        mAsyncHttpClient = new AsyncHttpClient();
        mCookieStore = new PersistentCookieStore(context);
        mAsyncHttpClient.setCookieStore(mCookieStore);
    }

    public static boolean isKnownError(@NotNull final Throwable e) {
        if (e.getMessage() == null)
            return false;
        for (final String error : knownErrorList) {
            if (e.getMessage().contains(error)) {
                return true;
            }
        }
        return false;
    }

    public void cleanCookies() {
        mCookieStore.clear();
    }

    @Override
    public <T> void executeRequest(@NotNull final Request<T> request, @NotNull final RequestExecutorListener<T> listener) {
        final String url = request.getRequestURL();
        final AsyncTask<byte[], Void, ResultSet<T>> backgroundParser = new AsyncTask<byte[], Void, ResultSet<T>>() {

            @Nullable
            @Override
            protected ResultSet<T> doInBackground(final byte[]... params) {
                try {
                    final long startTime = System.currentTimeMillis();
                    final ResultSet<T> result = new ResultSet<>(request.parseResponse(params[0]), null);
                    final long stopTime = System.currentTimeMillis();
                    Log.d("performance", "result parsing:" + (stopTime - startTime) + "ms");
                    return result;
                } catch (@NotNull final Exception e) {
                    if (!isKnownError(e))
                        Crashlytics.logException(e);

                    final String loc = Localization.getString(e.getMessage());
                    if (!loc.isEmpty()) {
                        return new ResultSet<>(null, new Exception(loc, e));
                    }

                    return new ResultSet<>(null, e);
                }
            }

            @Override
            protected void onPostExecute(@NotNull final ResultSet<T> t) {
                if (t.error != null) {
                    listener.onError(t.error);
                } else {
                    listener.onSuccess(t.result);
                }
            }
        };

        final AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final byte[] responseBody) {
                backgroundParser.execute(responseBody);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final byte[] responseBody, final Throwable error) {
                listener.onError(new Exception(error));
            }

            @Override
            public void onProgress(final int bytesWritten, final int totalSize) {
                // super.onProgress(bytesWritten, totalSize); // disabled download progress log
            }
        };

        final ArrayList<Header> headerList = new ArrayList<>();
        for (final Map.Entry<String, String> entry : request.getHeaderFields().entrySet()) {
            final Header header = new BasicHeader(entry.getKey(), entry.getValue());
            headerList.add(header);
        }
        final Header[] headers = headerList.toArray(new Header[headerList.size()]);

        switch (request.getHTTPMethod()) {
            case GET:
                mAsyncHttpClient.get(null, url, headers, null, responseHandler);
                break;
            case POST:
                StringEntity stringEntity = null;
                try {
                    stringEntity = new StringEntity(request.getBody());
                } catch (@NotNull final UnsupportedEncodingException e) {
                    listener.onError(e);
                }
                mAsyncHttpClient.post(null, url, headers, stringEntity, request.getContentType(), responseHandler);
                break;
            case PUT:
                listener.onError(new UnsupportedOperationException("AsyncHTTPRequestExecutor has no implementation for put, yet"));
                break;
            case DELETE:
                listener.onError(new UnsupportedOperationException("AsyncHTTPRequestExecutor has no implementation for delete, yet"));
                break;
            default:
                break;
        }

    }

    @Override
    public <T> void cancelRequest(final Request<T> request) {

    }

    private static class ResultSet<T> {
        T result;
        Exception error;

        private ResultSet(final T result, final Exception error) {
            this.result = result;
            this.error = error;
        }
    }
}
