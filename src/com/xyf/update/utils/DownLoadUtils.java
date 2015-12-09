package com.xyf.update.utils;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class DownLoadUtils {

    private static final class Util {
        public static final DownLoadUtils INSTACNES = new DownLoadUtils();
    }

    public static DownLoadUtils getInstances()
    {
        return Util.INSTACNES;
    }

    public void down(Context mContext ,String url , long totoalSize,int no)
    {
        new DownLoadThread(mContext,url,totoalSize,no).start();
    }

    public int getThreadNo()
    {
        return  (int) (System.currentTimeMillis() / 1000 % 1000);
    }

    private class DownLoadThread extends Thread {

        private Context mContext;
        private String url;
        private long localsize = 0;
        private long totalsize = 0;
        private int threadNo;

        private long lastSendTime = 0;
        private static final long TIME_SPEED = 100;

        public DownLoadThread(Context mContext,String url,long total,int threadNo)
        {
            this.mContext = mContext;
            this.url = url;
            this.totalsize = total;
            this.threadNo = threadNo;
        }

        @Override
        public void run() {
            super.run();

            URL mUrl = null;
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try{
                mUrl = new URL(url);
                conn = (HttpURLConnection) mUrl.openConnection();

                String filepath = FileUtils.getExtenralPath(mContext) + File.separator + threadNo + ".apk";
                File dstFile = new File(filepath);
                if (dstFile.exists())
                {
                    dstFile.delete();
                }
                raf = new RandomAccessFile(filepath,"rw");

                localsize = raf.length();
                totalsize = conn.getContentLength();

                LogUtils.i(DownLoadThread.class.getSimpleName(),String.format("filesize:[%d]",totalsize));

                InputStream is = conn.getInputStream();
                int rs = -1;
                byte[] buf = new byte[1024];

                while ((rs = is.read(buf)) != -1)
                {
                    raf.write(buf,0,rs);

                    localsize += rs;
                    LogUtils.i(threadNo + "","download size ... ... " + localsize);

                    long currentTime = System.currentTimeMillis();
                    if (currentTime -lastSendTime >= TIME_SPEED || localsize == totalsize)
                    {
                        Intent intent = new Intent(Config.DownLoad_Action + "." + threadNo);
                        intent.putExtra(Config.DownLoad_Percent,localsize);
                        intent.putExtra(Config.DownLoad_Total,totalsize);
                        intent.putExtra(Config.DownLoad_Path,filepath);

                        mContext.sendBroadcast(intent);

                        int progress = (int) (localsize * 100 / totalsize);
                        NotificationUtils.getInstances().showNotification(mContext,progress,filepath,threadNo);

                        lastSendTime = currentTime;
                    }

                }

                is.close();
                raf.close();

            }
            catch (Exception e)
            {
                LogUtils.error(e);
            }


        }


    }

}
