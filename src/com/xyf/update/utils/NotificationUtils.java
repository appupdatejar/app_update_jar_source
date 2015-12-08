package com.xyf.update.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.xyf.update.viewmodel.notification_layout;

import java.io.File;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class NotificationUtils {

    private static final class Util {
        public static final NotificationUtils INSTANCES = new NotificationUtils();
    }

    public static NotificationUtils getInstances()
    {
        return Util.INSTANCES;
    }

    public void showNotification(Context mContext,int progress,String path,int nNumber)
    {
        try
        {

            LogUtils.i(NotificationUtils.class.getSimpleName(),"progress:" + progress);
            NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification n = new Notification(ResourceUtils.getInstances().getIdByName(mContext,ResourceNames.iconName,ResourceType.drawable),"download",System.currentTimeMillis());
            n.flags = Notification.FLAG_AUTO_CANCEL;

            RemoteViews rv = new RemoteViews(mContext.getPackageName(),ResourceUtils.getInstances().getIdByName(mContext,notification_layout.class.getSimpleName(),ResourceType.layout));
            notification_layout m_layout = (notification_layout) ResourceUtils.getInstances().getViewObjectByClass(mContext,ResourceUtils.getInstances().getViewByName(mContext,notification_layout.class.getSimpleName()),notification_layout.class);
            rv.setImageViewResource(m_layout.getUpdate_notification_icon().getId(),ResourceUtils.getInstances().getIdByName(mContext,ResourceNames.iconName,ResourceType.drawable));
            rv.setProgressBar(m_layout.getUpdate_notification_progress().getId(),100,progress,false);
            rv.setTextViewText(m_layout.getUpdate_notification_progressnumber().getId(),String.format("%d%s",progress,mContext.getResources().getString(ResourceUtils.getInstances().getIdByName(mContext, ResourceNames.precent,ResourceType.string))));

            n.contentView = rv;

            if (progress == 100)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");

                PendingIntent pi = PendingIntent.getActivity(mContext,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                n.contentIntent = pi;
            }

            nm.notify(nNumber,n);
        }
        catch (Exception e)
        {
            LogUtils.error(e);
        }
    }

}
