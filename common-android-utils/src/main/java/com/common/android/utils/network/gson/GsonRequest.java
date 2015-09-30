package common.android.utils.network.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import common.android.utils.network.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by conrad on 2015/03/12.
 */
public abstract class GsonRequest<T> extends Request<T> {

    @Nullable
    public abstract T parseResponseGSON(JsonElement jsonObject) throws Exception;

    @Nullable
    @Override
    public T parseResponse(@NotNull final InputStream response) throws Exception {
        final JsonParser p = new JsonParser();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        final JsonElement parsedElement = p.parse(reader);
        return parseResponseGSON(parsedElement);
    }

    @Nullable
    @Override
    public T parseResponse(@Nullable final byte[] result) throws Exception {
        if (result == null || result.length < 1)
            throw new IllegalArgumentException("parseResponse: result empty");

        final JsonParser p = new JsonParser();
        final JsonElement parsedElement = p.parse(new String(result));
        return parseResponseGSON(parsedElement);
    }
}
