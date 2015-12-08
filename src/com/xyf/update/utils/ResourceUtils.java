package com.xyf.update.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class ResourceUtils {

    private static final class Utils {
        public static final ResourceUtils INSTANCES = new ResourceUtils();
    }

    public static ResourceUtils getInstances()
    {
        return Utils.INSTANCES;
    }

    public View getViewByName(Context mContext ,String name)
    {
        try{
            View contentView = LayoutInflater.from(mContext).inflate(getIdByName(mContext,name,ResourceType.layout),null);

            return contentView;
        }
        catch (Exception e)
        {
            LogUtils.error(e);
        }

        return null;
    }

    public Object getViewObjectByClass(Context mContext,View contentView,Class<?> clz)
    {
        try
        {
            Object obj = clz.newInstance();

            for (Field tmp : clz.getDeclaredFields())
            {
                try{
                    tmp.setAccessible(true);
                    tmp.set(obj,contentView.findViewById(getIdByName(mContext,tmp.getName(),ResourceType.id)));
                }
                catch (Exception e)
                {
                    LogUtils.error(e);
                }
            }

            return obj;
        }
        catch (Exception e)
        {
            LogUtils.error(e);
        }

        return null;
    }

    public int getIdByName(Context mContext,String name,ResourceType tt)
    {
        int id = -1;

        String type = getResourceType(tt);
        if (type.equals("unknow"))
        {
            return id;
        }


        String packagename = mContext.getPackageName();

        id = mContext.getResources().getIdentifier(name,type,packagename);

        return id;

    }

    private String getResourceType(ResourceType tt)
    {
        String type = "";
        switch (tt)
        {
            case drawable:
                type = "drawable";
                break;

            case id:
                type = "id";
                break;

            case string:
                type = "string";
                break;

            case layout:
                type = "layout";
                break;

            case anim:
                type = "anim";
                break;

            default:
                type = "unknow";
                break;
        }

        return type;
    }

}
