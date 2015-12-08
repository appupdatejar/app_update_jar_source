package com.xyf.update.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class FileUtils {

    public static String getExtenralPath(Context mContext)
    {
        String path = "";
        try{
            path = mContext.getExternalCacheDir().getAbsolutePath();
        }catch (Exception e)
        {
            try{
                path = Environment.getExternalStorageDirectory().getAbsolutePath();
            }catch (Exception e2)
            {
                path = mContext.getFilesDir().getAbsolutePath();
            }
        }

        return path;
    }

}
