package com.example.yashwanth.projectnothing.EventCode;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Yashwanth on 17-Jan-17.
 */

public class VolleySingleton {
    private static VolleySingleton v_instance=null;
    RequestQueue requestQueue;
    ImageLoader imageLoader;
       public VolleySingleton()
    {
        requestQueue= getRequestQueue();
        imageLoader= new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            LruCache<String,Bitmap> cache= new LruCache<>((int)((Runtime.getRuntime().maxMemory()/1024)/8));
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }
    public static VolleySingleton getInstance()
    {
        if(v_instance==null)
            v_instance=new VolleySingleton();
        return v_instance;
    }
    public  RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        }
        return requestQueue;
    }


    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }
}
