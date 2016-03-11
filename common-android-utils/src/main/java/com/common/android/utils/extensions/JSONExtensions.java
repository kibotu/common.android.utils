package com.common.android.utils.extensions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.misc.GsonProvider.getGson;

final public class JSONExtensions {

    private JSONExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Serializes json to object.
     *
     * @param value json.
     * @return Object
     */
    @Nullable
    public static Object objectForJSON(@Nullable final Object value) {
        if (value == null) return null;

        if (value instanceof Map) {
            final JSONObject dest = new JSONObject();

            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) value;

            for (final Map.Entry<String, Object> entry : map.entrySet()) {
                safePut(dest, entry.getKey(), objectForJSON(entry.getValue()));
            }
            return dest;
        } else if (value instanceof Object[]) {
            final JSONArray dest = new JSONArray();

            final Object[] array = (Object[]) value;
            for (final Object val : array) {
                dest.put(objectForJSON(val));
            }
            return dest;
        } else if (value instanceof List) {
            final JSONArray dest = new JSONArray();

            final List list = (List) value;
            for (final Object val : list) {
                dest.put(objectForJSON(val));
            }
            return dest;
        } else {
            return value;
        }
    }

    /**
     * Json string to JSONObject.
     *
     * @param json Json String.
     * @return JSONObject.
     */
    @Nullable
    public static JSONObject saveStringToJSONObject(@NonNull final String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Json string to JSONArray.
     *
     * @param json Json String.
     * @return JSONArray.
     */
    @Nullable
    public static JSONArray saveStringToJsonArray(@NonNull final String json) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Nullable
    public static JSONObject loadJsonFromAssets(@NonNull final String file) {
        JSONObject json = null;
        try {
            final StringBuffer buffer = new StringBuffer();
            final BufferedReader in = new BufferedReader(new InputStreamReader(getContext().getAssets().open(file), "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buffer.append(str);
            }
            in.close();

            json = new JSONObject(new JSONTokener(buffer.toString()));

        } catch (@NonNull final Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @NonNull
    public static JSONObject fromJson(@NonNull final String json) {
        try {
            return new JSONObject(json);
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @NonNull
    public static String getString(@NonNull final JSONObject jsonObject, final String name) {
        try {
            return jsonObject.getString(name);
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Concatenates multiple json arrays.
     *
     * @param arguments Multiple JSon Arrays.
     * @return Single Json Array.
     * @throws org.json.JSONException
     */
    @NonNull
    public JSONArray concatArray(@NonNull final JSONArray... arguments) throws JSONException {
        final JSONArray result = new JSONArray();
        for (final JSONArray arr : arguments) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }

    @Nullable
    public static JSONObject getObjectOptional(@NonNull final JSONObject object, final String name) {
        JSONObject result = null;
        try {
            result = object.getJSONObject(name);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Nullable
    public static JSONObject getObjectOptional(@NonNull final JSONArray jsonArray, final int index) {
        JSONObject item = null;
        try {
            item = jsonArray.getJSONObject(index);
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Nullable
    public static JSONArray getArrayOptional(@NonNull final JSONObject object, final String name) {

        JSONArray result = null;

        try {
            result = object.getJSONArray(name);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Nullable
    public static JSONObject getArrayObject(@NonNull final JSONArray array, final int index) {

        JSONObject result = null;

        try {
            result = array.getJSONObject(index);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @NonNull
    public static String getStringOptional(@Nullable final JSONObject object, final String name) {
        String result = "";

        if (object == null)
            return result;

        try {
            result = object.getString(name);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static int getInteger(@NonNull final JSONObject object, final String name) {

        int result = 0;

        try {
            result = object.getInt(name);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static long getLong(@NonNull final JSONObject object, final String name) {
        long result = 0;

        try {
            result = object.getLong(name);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static double getDouble(@NonNull final JSONObject object, final String name) {

        double result = 0;
        try {
            result = object.getDouble(name);
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @NonNull
    public static ArrayList<Integer> getIntegerArrayList(@NonNull final JSONObject object, final String name) {

        final ArrayList<Integer> result = new ArrayList<Integer>();

        try {
            final JSONArray jsona = object.getJSONArray(name);
            for (int i = 0; i < jsona.length(); i++) {
                result.add(jsona.getInt(i));
            }
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static boolean getBoolean(@NonNull final JSONObject object, final String name) {

        boolean result = false;

        try {
            final String resultString = object.getString(name);
            result = !TextUtils.isEmpty(resultString) && (resultString.equals("true"));
        } catch (@NonNull final Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * Catches {@link JSONException} while putting key/value pair into json.
     *
     * @param obj Container.
     * @param key Key.
     * @param val Value.
     */
    public static void safePut(@NonNull final JSONObject obj, @NonNull final String key, @Nullable final Object val) {
        try {
            obj.put(key, objectForJSON(val));
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends if key exists. Otherwise @see #safePut(org.json.JSONObject, String, Object)
     */
    public static void safeAppendToJsonArray(@NonNull final JSONObject obj, @NonNull final String key, @Nullable final JSONArray val) {
        try {
            if (!obj.has(key))
                obj.put(key, objectForJSON(val));
            else
                for (int i = 0; i < (val != null ? val.length() : 0); ++i)
                    obj.getJSONArray(key).put(val.get(i));
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
    }

    public static void safePutAppendString(@NonNull final JSONObject obj, @NonNull final String key, @Nullable final String val) {
        try {
            if (!obj.has(key))
                obj.put(key, objectForJSON(val));
            else
                obj.put(key, obj.getString(key).concat(" ").concat(val));
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes sure there won't be put a null object to a key.
     *
     * @see #safePut(JSONObject, String, Object)
     */
    public static void safePutOpt(@NonNull final JSONObject obj, @NonNull final String key, @Nullable final Object val) {
        if (val != null && !val.equals("")) {
            safePut(obj, key, val);
        } else {
            obj.remove(key);
        }
    }

    /**
     * Deep clones a json object.
     *
     * @param source Source json.
     * @return Cloned json.
     */
    @Nullable
    public static JSONObject deepClone(@NonNull final JSONObject source) {
        final JSONObject dest = null;
        final Iterator keys = source.keys();
        while (keys.hasNext()) {
            final String key = (String) keys.next();
            Object value = source.opt(key);
            if (value != null) {
                if (value instanceof JSONObject) {
                    value = deepClone((JSONObject) value);
                }
                safePut(dest, key, value);
            }
        }
        return dest;
    }

    /**
     * Merges source json into destination json.
     *
     * @param dest   Destination json.
     * @param source Source json.
     */
    @Nullable
    public static void merge(@NonNull final JSONObject dest, @NonNull final JSONObject source) {
        if (dest == null || source == null) return;
        final Iterator keys = source.keys();
        while (keys.hasNext()) {
            final String key = (String) keys.next();
            final Object sourceValue = source.opt(key);
            if (sourceValue != null) {
                final Object destValue = dest.opt(key);
                if (destValue != null && sourceValue instanceof JSONObject && destValue instanceof JSONObject) {
                    merge((JSONObject) destValue, (JSONObject) sourceValue);
                } else {
                    safePut(dest, key, sourceValue);
                }
            }
        }
    }


    /**
     * Json string to JSONObject.
     *
     * @param json Json String.
     * @return JSONObject.
     */
    @Nullable
    public static JSONObject parseJSONObject(@NonNull final String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Json string to JSONArray.
     *
     * @param json Json String.
     * @return JSONArray.
     */
    @Nullable
    public static JSONArray parseJSONArray(@NonNull final String json) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (@NonNull final JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Nullable
    public static JSONObject loadJson(@NonNull final String filePath) {
        JSONObject json = new JSONObject();

        try {
            final StringBuffer buffer = new StringBuffer();
            final BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            json = new JSONObject(new JSONTokener(buffer.toString()));
        } catch (@NonNull final Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    @Nullable
    public static JSONObject loadJsonFromAssets(@NonNull final Context context, @NonNull final String file) {
        JSONObject json = null;
        try {
            final StringBuffer buffer = new StringBuffer();
            final BufferedReader in = new BufferedReader(new InputStreamReader(context.getAssets().open(file), "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buffer.append(str);
            }
            in.close();

            json = new JSONObject(new JSONTokener(buffer.toString()));

        } catch (@NonNull final Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @Nullable
    public static JSONObject getObjectOptional(final String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (final JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static <T> List<T> asList(@NonNull final String json, @NonNull final Class<T[]> type) {
        return Arrays.asList(getGson().fromJson(json, type));
    }

    /**
     * use {@link #asList} instead
     */
    @Deprecated
    public static <T> List<T> toList(@NonNull final String json) {
        final Type listType = new TypeToken<List<T>>() {
        }.getType();
        return getGson().fromJson(json, listType);
    }
}