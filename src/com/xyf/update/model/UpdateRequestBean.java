package com.xyf.update.model;

import java.io.Serializable;

/**
 * Created by shxiayf on 2015/12/7.
 */
public class UpdateRequestBean implements Serializable {

    private String version;
    private String packageName;
    private String sdkVersion;

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
