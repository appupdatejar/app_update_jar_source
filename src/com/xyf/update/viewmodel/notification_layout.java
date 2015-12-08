package com.xyf.update.viewmodel;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class notification_layout implements Serializable {

    private ImageView update_notification_icon;
    private ProgressBar update_notification_progress;
    private TextView update_notification_progressnumber;

    public ImageView getUpdate_notification_icon() {
        return update_notification_icon;
    }

    public ProgressBar getUpdate_notification_progress() {
        return update_notification_progress;
    }

    public TextView getUpdate_notification_progressnumber() {
        return update_notification_progressnumber;
    }
}
