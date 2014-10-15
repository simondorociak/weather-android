package sk.simondorociak.weather.android.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import sk.simondorociak.weather.android.commons.BitmapLruCache;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class AppController extends Application {

    private static final String DEFAULT_TAG = AppController.class.getSimpleName();
    private static AppController instance;
    private static Context context;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    /**
     * Returns instance of AppController class.
     * @return
     */
    public static AppController getInstance() {
        return instance;
    }

    /**
     * Returns instance of RequestQueue as Singleton.
     * @return
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Gets current ImageLoader as Singleton.
     * @return
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
        }
        return imageLoader;
    }

    /**
     * Adds provided request to request queue with specified tag.
     * @param request request to add
     * @param tag tag of request
     * @param <T>
     */
    public <T> void addRequestToQueue(final Request<T> request, final String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? DEFAULT_TAG : tag);
        getRequestQueue().add(request);
    }

    /**
     * Adds provided request to request queue with default tag.
     * @param request request to add
     * @param <T>
     */
    public <T> void addRequestToQueue(final Request<T> request) {
        // set default tag to request
        request.setTag(DEFAULT_TAG);
        getRequestQueue().add(request);
    }

    /**
     * Cancels all pending requests which match tag.
     * @param tag tag of request to cancel
     */
    public void cancelPendingRequests(final String tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    /**
     * Cancels all pending requests with default tag.
     */
    public void cancelPendingRequests() {
        if (requestQueue != null) {
            requestQueue.cancelAll(DEFAULT_TAG);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }

    /**
     * Returns current application context.
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
