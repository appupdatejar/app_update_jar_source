package com.xyf.update.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import com.xyf.update.utils.*;
import com.xyf.update.viewmodel.download_layout;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class UpdateDailog extends Dialog {

    private Context mContext;
    private int canNotShowDialog;
    private int threadNo;
    public UpdateDailog(Context context,int canNotShowDialog,int threadNo) {
        super(context,android.R.style.Theme_Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.mContext = context;
        this.canNotShowDialog = canNotShowDialog;
        this.threadNo = threadNo;
        initDialog();
    }

    private download_layout mDownloadLayout;
    private View contentView;
    private void initDialog() {
        contentView = ResourceUtils.getInstances().getViewByName(mContext,download_layout.class.getSimpleName());
        if (contentView == null)
        {
            dismiss();
            return;
        }
        mDownloadLayout = (download_layout) ResourceUtils.getInstances().getViewObjectByClass(mContext,contentView,download_layout.class);
        if (mDownloadLayout == null)
        {
            dismiss();
            return;
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.DownLoad_Action + "."  + threadNo);
        mContext.registerReceiver(myBroadcastReceiver,intentFilter);

        updateViews( 100, 0, "");

        setContentView(contentView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    private void updateViews(long total,long percent,String path)
    {
        if (percent == 0)
        {
            mDownloadLayout.getUpdate_download_progressnumber().setText("0%");
            mDownloadLayout.getUpdate_download_progress().setProgress(0);
        }
        else if (total == percent)
        {
            mDownloadLayout.getUpdate_download_progressnumber().setText("100%");
            mDownloadLayout.getUpdate_download_progress().setProgress(100);

            ApkUtils.getInstances().InstallApk(mContext,path);
            mContext.unregisterReceiver(myBroadcastReceiver);

            dismiss();
        }
        else
        {
            int pp = (int) (percent * 100 / total);
            mDownloadLayout.getUpdate_download_progress().setProgress(pp);
            mDownloadLayout.getUpdate_download_progressnumber().setText(String.format("%d%s",pp,mContext.getResources().getString(ResourceUtils.getInstances().getIdByName(mContext, ResourceNames.precent,ResourceType.string))));
        }

    }

    private BroadcastReceiver myBroadcastReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String currentAction = Config.DownLoad_Action + "." + threadNo;
            if (intent.getAction().equals(currentAction))
            {
                try{
                    long total = intent.getLongExtra(Config.DownLoad_Total,0);
                    long percent = intent.getLongExtra(Config.DownLoad_Percent,0);
                    String path = intent.getStringExtra(Config.DownLoad_Path);
                    updateViews(total,percent,path);
                }catch (Exception e)
                {
                    LogUtils.error(e);
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if (canNotShowDialog != 0)
            {
                mContext.unregisterReceiver(myBroadcastReceiver);
                dismiss();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
