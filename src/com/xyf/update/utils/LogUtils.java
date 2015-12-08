package com.xyf.update.utils;

import android.util.Log;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class LogUtils {

    public static void i (String ...args)
    {
        if (args.length != 2)
        {
            return;
        }

        if (!Config.Debug)
        {
            return;
        }

        Log.i(args[0],args[1]);
    }

    public static void error(Exception e)
    {
        if (!Config.Debug)
        {
            return;
        }

        e.printStackTrace();
    }

}
