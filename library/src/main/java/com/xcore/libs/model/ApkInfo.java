package com.xcore.libs.model;

public class ApkInfo {

    private String appName;
    private String packageName;
    private String version;
    private int versionCode;

    public ApkInfo(String appName, String packageName, String version, int versionCode) {
        this.appName = appName;
        this.packageName = packageName;
        this.version = version;
        this.versionCode = versionCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}

