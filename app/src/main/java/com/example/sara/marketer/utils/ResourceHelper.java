package com.example.sara.marketer.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.sara.marketer.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sara on 11/21/17.
 */

public class ResourceHelper {

    public static List<TypedArray> getMultiTypedArray(Context context, String key) {
        List<TypedArray> array = new ArrayList<>();

        try {
            Class<R.array> res = R.array.class;
            Field field;
            int counter = 0;

            do {
                field = res.getField(key + "_" + counter);
                array.add(context.getResources().obtainTypedArray(field.getInt(null)));
                counter++;
            } while (field != null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }
}
