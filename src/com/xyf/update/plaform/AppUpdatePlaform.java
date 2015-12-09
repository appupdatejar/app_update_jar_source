package com.xyf.update.plaform;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.DownloadListener;
import com.xyf.update.model.UpdateRequestBean;
import com.xyf.update.model.UpdateResponseBean;
import com.xyf.update.utils.Config;
import com.xyf.update.utils.DownLoadUtils;
import com.xyf.update.utils.HttpUrlUtils;
import com.xyf.update.utils.JsonUtils;
import com.xyf.update.view.UpdateDailog;

import java.io.InputStream;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class AppUpdatePlaform {

    private AppUpdatePlaform () {}

    private static final class Util {
        public static final AppUpdatePlaform INSTANCES = new AppUpdatePlaform();
    }

    public static AppUpdatePlaform getInstances()
    {
        return Util.INSTANCES;
    }

    private boolean isShowDialog = true;

    public void setIsShowDialog(boolean isShowDialog) {
        this.isShowDialog = isShowDialog;
    }


    private Context mContext;
    private AppUpdateListener callback;
    public void checkUpdate(Context mm,String version, final AppUpdateListener listener)
    {
        this.mContext = mm;
        this.callback = listener;

        UpdateRequestBean mRequestBean = new UpdateRequestBean();
        mRequestBean.setPackageName(mContext.getPackageName());
        mRequestBean.setVersion(version);
        mRequestBean.setSdkVersion(Config.Version);

        String requestContent = JsonUtils.parseObj2String(mRequestBean);

        showProgress(mContext);
        HttpUrlUtils.getInstances().Post(Config.UPDATE_URL, requestContent, new HttpUrlUtils.HttpResultListener() {
            @Override
            public void onResponse(InputStream is) {
                dismissDialog();
                if (is == null)
                {
                    callback.updateApp(AppUpdateListener.RQUEST_ERR);
                }
                else
                {
                    String responseContent = HttpUrlUtils.getInstances().parseStream2String(is);
                    final UpdateResponseBean responseBean = (UpdateResponseBean) JsonUtils.parseString2Obj(responseContent,UpdateResponseBean.class);

                    if (responseBean.getIsUpdate() == 1)
                    {
                        callback.updateApp(AppUpdateListener.NEED_UPDATE);

                        final int threadNo = DownLoadUtils.getInstances().getThreadNo();

                        if (isShowDialog)
                        {
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new UpdateDailog(mContext,responseBean.getCanNotShowDialog(),threadNo).show();
                                }
                            });
                        }

                        DownLoadUtils.getInstances().down(mContext,responseBean.getUrl(),responseBean.getFilesize(),threadNo);

                    }
                    else
                    {
                        callback.updateApp(AppUpdateListener.DONT_UPDATE);
                    }

                }

            }
        });



    }


    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    private ProgressDialog mDialog;
    private void showProgress(Context mContext)
    {
        if (mDialog == null)
        {
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("loading ...");
            mDialog.setCancelable(false);
            mDialog.setIndeterminate(true);

            mDialog.show();
        }
    }

    private void dismissDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
