package com.xyf.AppUpdateJar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.xyf.update.plaform.AppUpdateListener;
import com.xyf.update.plaform.AppUpdatePlaform;
import com.xyf.update.utils.LogUtils;

public class TestActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppUpdatePlaform.getInstances().checkUpdate(TestActivity.this, "100", new AppUpdateListener() {
                    @Override
                    public void updateApp(int result) {
                        LogUtils.i(TestActivity.class.getName(),"result:"+result);
                    }
                });

            }
        });


    }
}
