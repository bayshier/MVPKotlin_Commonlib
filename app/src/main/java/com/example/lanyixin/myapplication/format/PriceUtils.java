package com.example.lanyixin.myapplication.format;


import android.content.Context;
import com.example.lanyixin.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;


public class PriceUtils {

    /**
     * 获取配置json 位置：Raw Resource
     */
    public static String getConfigJson(int res, Context context) {
        try {
            InputStream is = context.getResources().openRawResource(res);
            byte[] buf = new byte[is.available()];//读取整个文件
            is.read(buf);
            is.close();
            return byteToString(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String byteToString(byte[] data) {
        String result = "";
        try {
            result = new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return result;
    }

}