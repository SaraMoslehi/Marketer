package com.example.sara.marketer.utils;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by sara on 12/4/17.
 */

public class ImageUtil {
    public static RequestOptions DefaultOption() {
        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.mipmap.ic_launcher);
//        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        return requestOptions;
    }

}
