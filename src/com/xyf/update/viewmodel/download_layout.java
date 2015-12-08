package com.xyf.update.viewmodel;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class download_layout implements Serializable {

    private TextView update_download_title;
    private View update_download_line;
    private RelativeLayout update_download_rl;
    private ProgressBar update_download_progress;
    private TextView update_download_progressnumber;

    public TextView getUpdate_download_title() {
        return update_download_title;
    }

    public View getUpdate_download_line() {
        return update_download_line;
    }

    public RelativeLayout getUpdate_download_rl() {
        return update_download_rl;
    }

    public ProgressBar getUpdate_download_progress() {
        return update_download_progress;
    }

    public TextView getUpdate_download_progressnumber() {
        return update_download_progressnumber;
    }
}
