package com.common.android.utils.network;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 26/10/15.
 */
public class RequestQueueProvider {

    static RequestQueue requestQueue;

    private RequestQueueProvider() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static void createRequestQueue() {
        // Instantiate the cache
        final Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        final Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        RequestQueueProvider.requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null)
            createRequestQueue();
        return requestQueue;
    }
}