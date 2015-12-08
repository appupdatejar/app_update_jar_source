package com.xyf.update.plaform;

/**
 * Created by shxiayf on 2015/12/7.
 */
public interface AppUpdateListener {

    public static final int NEED_UPDATE = 0;
    public static final int DONT_UPDATE = 1;
    public static final int RQUEST_ERR = 2;

    public void updateApp(int result);

}
