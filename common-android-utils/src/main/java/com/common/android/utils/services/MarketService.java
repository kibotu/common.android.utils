/*
 * Copyright 2011 - AndroidQuery.com (tinyeeliu@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package common.android.utils.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.Html.TagHandler;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.adviqo.app.misc.FontCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.util.Locale;

public class MarketService {

    /**
     * Update check level REVISION.
     */
    public static final int REVISION = 0;
    /**
     * Update check level MINOR.
     */
    public static final int MINOR = 1;
    /**
     * Update check level MAJOR.
     */
    public static final int MAJOR = 2;
    private static final String SKIP_VERSION = "aqs.skip";
    private static final String BULLET = "•";
    private static ApplicationInfo ai;
    private static PackageInfo pi;
    private Activity act;
    private AQuery aq;
    private Handler handler;
    private String locale;
    private String rateUrl;
    private String updateUrl;
    private boolean force;
    private int progress;
    private long expire = 12 * 60 * 1000;
    private String version;
    private boolean fetch;
    private boolean completed;
    private int level = REVISION;


    /**
     * Instantiates a new MarketService.
     *
     * @param act Current activity.
     */

    public MarketService(final Activity act) {
        this.act = act;
        this.aq = new AQuery(act);
        this.handler = new Handler();
        this.locale = Locale.getDefault().toString();
        this.rateUrl = getMarketUrl();
        this.updateUrl = rateUrl;
    }

    private static boolean openUrl(@NotNull final Activity act, @Nullable final String url) {


        try {

            if (url == null) return false;

            final Uri uri = Uri.parse(url);
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            act.startActivity(intent);

            return true;
        } catch (@NotNull final Exception e) {
            return false;
        }
    }

    @NotNull
    private static String patchBody(final String body) {
        return "<small>" + body + "</small>";
    }

    private static void setSkipVersion(@NotNull final Context context, final String version) {

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(SKIP_VERSION, version).commit();
    }

    @org.jetbrains.annotations.Nullable
    private static String getSkipVersion(@NotNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SKIP_VERSION, null);
    }

    /**
     * Set the destination url of the default rate/review button.
     *
     * @param url url
     * @return self
     */
    @NotNull
    public MarketService rateUrl(final String url) {
        this.rateUrl = url;
        return this;
    }

    /**
     * Set the update check granularity level. Default is REVISION.
     * <p/>
     * <br>
     * <p/>
     * Can be REVISION, MINOR, or MAJOR.
     * <p/>
     * <br>
     * <p/>
     * App version format: MAJOR.MINOR.REVISION
     * <p/>
     * <br>
     * <p/>
     * Example:
     * <p/>
     * <br>
     * Current app version: 3.1.2
     * <br>
     * Newest app version: 3.1.4
     * <br>
     * Update notice will show if level is REVISION, because the revision code is higher.
     * <br>
     * Update notice will NOT show if level is MINOR, because the minor code is equal (or higher).
     *
     * @param level granularity level
     * @return self
     */
    @NotNull
    public MarketService level(final int level) {
        this.level = level;
        return this;
    }

    /**
     * Set the destination url of the default update button.
     *
     * @param url url
     * @return self
     */
    @NotNull
    public MarketService updateUrl(final String url) {
        this.updateUrl = url;
        return this;
    }

    /**
     * Force the update dialog to a specific locale. Example: en_US, ja_JP.
     *
     * @param locale interface locale
     * @return self
     */

    @NotNull
    public MarketService locale(final String locale) {
        this.locale = locale;
        return this;
    }

    /**
     * Display a progress view during version check.
     *
     * @param id view id
     * @return self
     */
    @NotNull
    public MarketService progress(final int id) {
        this.progress = id;
        return this;
    }

    /**
     * Force a version check against the AQuery server and show a dialog regardless of versions.
     *
     * @param force force an update check
     * @return self
     */
    @NotNull
    public MarketService force(final boolean force) {
        this.force = force;
        return this;
    }

    /**
     * The time duration which last version check expires. Default is 10 hours.
     *
     * @param expire expire time in milliseconds
     * @return self
     */

    @NotNull
    public MarketService expire(final long expire) {
        this.expire = expire;
        return this;
    }

    private ApplicationInfo getApplicationInfo() {

        if (ai == null) {
            ai = act.getApplicationInfo();
        }

        return ai;
    }

    private PackageInfo getPackageInfo() {

        if (pi == null) {
            try {
                pi = act.getPackageManager().getPackageInfo(getAppId(), 0);

            } catch (@NotNull final NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pi;
    }

    private String getHost() {

        return "https://androidquery.appspot.com";
    }

    @NotNull
    private String getQueryUrl() {
        final String appId = getAppId();
        String url = getHost() + "/api/market?app=" + appId + "&locale=" + locale + "&version=" + getVersion() + "&code=" + getVersionCode() + "&aq=" + AQuery.VERSION;
        if (force) {
            url += "&force=true";
        }
        return url;
    }

    private String getAppId() {

        return getApplicationInfo().packageName;
    }

    private Drawable getAppIcon() {
        final Drawable d = getApplicationInfo().loadIcon(act.getPackageManager());
        return d;
    }

    private String getVersion() {
        return getPackageInfo().versionName;
    }

    private int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * Perform a version check.
     */

    public void checkVersion() {

        final String url = getQueryUrl();

        final AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).handler(handler, "marketCb").fileCache(!force).expire(expire);

        aq.progress(progress).ajax(cb);

    }

    @NotNull
    private String getMarketUrl() {
        final String id = getAppId();
        return "market://details?id=" + id;
    }

    protected void callback(final String url, @Nullable final JSONObject jo, final AjaxStatus status) {


        if (jo == null) return;

        final String latestVer = jo.optString("version", "0");
        final int latestCode = jo.optInt("code", 0);

        AQUtility.debug("version", getVersion() + "->" + latestVer + ":" + getVersionCode() + "->" + latestCode);
        AQUtility.debug("outdated", outdated(latestVer, latestCode));

        if (force || outdated(latestVer, latestCode)) {
            showUpdateDialog(jo);
        }


    }

    private boolean outdated(@NotNull final String latestVer, final int latestCode) {

        final String skip = getSkipVersion(act);

        if (latestVer.equals(skip)) {
            return false;
        }

        final String version = getVersion();
        final int code = getVersionCode();

        if (!version.equals(latestVer)) {
            if (code <= latestCode) {
                //return true;
                return requireUpdate(version, latestVer, level);
            }
        }

        return false;
    }

    private boolean requireUpdate(@NotNull final String existVer, @NotNull final String latestVer, final int level) {

        if (existVer.equals(latestVer)) return false;

        try {

            final String[] evs = existVer.split("\\.");
            final String[] lvs = latestVer.split("\\.");

            if (evs.length < 3 || lvs.length < 3) return true;

            switch (level) {
                case REVISION:
                    if (!evs[evs.length - 1].equals(lvs[lvs.length - 1])) {
                        return true;
                    }
                case MINOR:
                    if (!evs[evs.length - 2].equals(lvs[lvs.length - 2])) {
                        return true;
                    }
                case MAJOR:
                    return !evs[evs.length - 3].equals(lvs[lvs.length - 3]);
                default:
                    return true;
            }

        } catch (@NotNull final Exception e) {
            AQUtility.report(e);
            return true;
        }

    }

    protected void showUpdateDialog(@Nullable final JSONObject jo) {

        if (jo == null || version != null) return;

        if (!isActive()) return;

        final JSONObject dia = jo.optJSONObject("dialog");

        final String update = dia.optString("update", "Aktualisieren");
        final String skip = dia.optString("skip", "Überspringen");
        final String rate = dia.optString("rate", "Bewerten");
        //String message = dia.optString("body", "");
        final String body = dia.optString("wbody", "");
        final String title = dia.optString("title", "Aktualisierung verfügbar");

        AQUtility.debug("wbody", body);

        version = jo.optString("version", null);

        final Drawable icon = getAppIcon();

        final Context context = act;

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setIcon(icon)
                .setTitle(title)
                .setPositiveButton(rate, handler)
                .setNeutralButton(skip, handler)
                .setNegativeButton(update, handler)
                .create();

        dialog.setMessage(Html.fromHtml(patchBody(body), null, handler));

        aq.show(dialog);

        final TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        messageView.setTypeface(FontCache.FontIstok.getFont());

        final TextView updateButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        updateButton.setTypeface(FontCache.FontIstok.getFont());
        final TextView neutralButton = dialog.getButton(Dialog.BUTTON_NEUTRAL);
        neutralButton.setTypeface(FontCache.FontIstok.getFont());
        final TextView negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        negativeButton.setTypeface(FontCache.FontIstok.getFont());

//        final TextView titleView = (TextView) dialog.findViewById(R.id.text);
//        titleView.setTypeface(FontCache.RosarioBold.getFont());
//
        return;

    }

    private boolean isActive() {
        return !act.isFinishing();
    }

    private class Handler implements DialogInterface.OnClickListener, TagHandler {

        @SuppressWarnings("unused")
        public void marketCb(final String url, @Nullable final JSONObject jo, @NotNull final AjaxStatus status) {

            if (act.isFinishing()) return;

            if (jo != null) {

                final String s = jo.optString("status");

                if ("1".equals(s)) {

                    if (jo.has("dialog")) {
                        cb(url, jo, status);
                    }

                    if (!fetch && jo.optBoolean("fetch", false) && status.getSource() == AjaxStatus.NETWORK) {

                        fetch = true;

                        final String marketUrl = jo.optString("marketUrl", null);

                        final AjaxCallback<String> cb = new AjaxCallback<String>();
                        cb.url(marketUrl).type(String.class).handler(this, "detailCb");
                        aq.progress(progress).ajax(cb);

                    }

                } else if ("0".equals(s)) {
                    status.invalidate();
                } else {
                    cb(url, jo, status);
                }

            } else {
                cb(url, jo, status);
            }
        }

        private void cb(final String url, final JSONObject jo, final AjaxStatus status) {

            if (!completed) {
                completed = true;
                progress = 0;
                callback(url, jo, status);
            }
        }

        @SuppressWarnings("unused")
        public void detailCb(final String url, @Nullable final String html, final AjaxStatus status) {

            if (html != null && html.length() > 1000) {

                final String qurl = getQueryUrl();

                final AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
                cb.url(qurl).type(JSONObject.class).handler(this, "marketCb");
                cb.param("html", html);

                aq.progress(progress).ajax(cb);

            }


        }


        @Override
        public void onClick(final DialogInterface dialog, final int which) {

            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    openUrl(act, rateUrl);
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    openUrl(act, updateUrl);
                    break;
                case AlertDialog.BUTTON_NEUTRAL:
                    setSkipVersion(act, version);
                    break;
            }


        }


        @Override
        public void handleTag(final boolean opening, final String tag, @NotNull final Editable output, final XMLReader xmlReader) {

            if ("li".equals(tag)) {

                if (opening) {
                    output.append("  ");
                    output.append(BULLET);
                    output.append("  ");
                } else {
                    output.append("\n");
                }


            }
        }

    }


}
