package sk.simondorociak.weather.android.commons;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Custom implementation of LruCache used for storing loaded Bitmaps from server.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    /**
     * Calculates and returns cache size used by this LruCache.
     * @return
     */
    private static int getDefaultCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    public BitmapLruCache() {
        this(getDefaultCacheSize());
    }

    @Override
    public Bitmap getBitmap(String key) {
        return get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        put(key, bitmap);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return value.getAllocationByteCount() / 1024;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return value.getByteCount() / 1024;
        }
        else {
            return (value.getRowBytes() * value.getHeight()) / 1024;
        }
    }
}
