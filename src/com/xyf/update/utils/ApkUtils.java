package com.xyf.update.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class ApkUtils {

    private static final class Util {
        public static final ApkUtils INSTANCES = new ApkUtils();
    }

    public static ApkUtils getInstances()
    {
        return Util.INSTANCES;
    }

    public void InstallApk(Context mContext,String path)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }


}
