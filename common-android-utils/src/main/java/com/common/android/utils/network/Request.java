package common.android.utils.network;

import android.util.Log;
import common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by conrad on 2015/03/09.
 */
public abstract class Request<T> implements ILogTag {

    @NotNull
    private HashMap<String, String> mHeaderFields = new HashMap<>();
    @NotNull
    private HashMap<String, String> mURLParameters = new HashMap<>();
    @NotNull
    private HashMap<String, List<String>> mFilterFlags = new HashMap<>();

    @Nullable
    public abstract T parseResponse(InputStream response) throws Exception;

    @Nullable
    public abstract T parseResponse(byte[] result) throws Exception;

    protected abstract String getRequestURLWithoutURLParameters();

    @NotNull
    public String getRequestURL() {
        return getRequestURLWithoutURLParameters() + getEncodedURLParameters() + getFilterParameters();
    }

    // region basic description

    private String getFilterParameters() {

        if (mFilterFlags.isEmpty())
            return "";

        final StringBuilder result = new StringBuilder();
        final String addition = "&";

        for (final Map.Entry<String, List<String>> entry : mFilterFlags.entrySet()) {
            try {
                final List<String> list = entry.getValue();
                for (final String value : list)
                    result.append(addition)
                            .append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                            .append("=")
                            .append(URLEncoder.encode(value, "UTF-8"));
            } catch (@NotNull final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    @NotNull
    public HTTPMethod getHTTPMethod() {
        return HTTPMethod.GET;
    }

    public boolean isSessionNeeded() {
        return false;
    }

    @Nullable
    public String getBody() {
        return null;
    }

    @NotNull
    public String getContentType() {
        return "application/json; charset=utf-8";
    }

    public String getEncodedURLParameter(final String key) {
        return mURLParameters.get(key);
    }

    //endregion

    // region headers and url parameters

    public String getEncodedURLParameters() {
        String result = "?";
        String addition = "";
        for (final Map.Entry<String, String> entry : mURLParameters.entrySet()) {
            try {
                result += addition;
                result += URLEncoder.encode(entry.getKey(), "UTF-8");
                final String value = entry.getValue();
                if (value == null) {
                    Log.v(tag(), "getEncodedURLParameters " + entry.getKey() + " value null");
                    continue;
                }
                if (value.length() > 0) {
                    result += "=";
                    result += URLEncoder.encode(entry.getValue(), "UTF-8");
                }
                addition = "&";
            } catch (@NotNull final UnsupportedEncodingException e) {
                Log.d("Encoding Error", e.getLocalizedMessage());
            }
        }
        return result;
    }

    @NotNull
    final public String tag() {
        return getClass().getSimpleName();
    }

    public void addURLParameter(final String key, final String value) {
        mURLParameters.put(key, value);
    }

    public void addHeaderField(final String key, final String value) {
        mHeaderFields.put(key, value);
    }

    public void addFilter(final String key, final String value) {
        if (!mFilterFlags.containsKey(key))
            mFilterFlags.put(key, new ArrayList<String>());

        mFilterFlags.get(key).add(value);
    }

    @NotNull
    public Map<String, String> getHeaderFields() {
        return mHeaderFields;
    }

    public enum HTTPMethod {
        GET, POST, PUT, DELETE
    }

//endregion

}
