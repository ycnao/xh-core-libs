package com.xcore.libs.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 实体类
 * author: created by 闹闹 on 2022/4/20
 * version: v1.0.0
 */
public class FileData implements Parcelable {

    private String name;
    private String path;
    private String time;
    private String size;
    private String type;
    private boolean isCheck;
    private MimeType fileType;

    public FileData() {
    }

    public FileData(String name, String path, String time, String size, String type, boolean isCheck, MimeType fileType) {
        this.name = name;
        this.path = path;
        this.time = time;
        this.size = size;
        this.type = type;
        this.isCheck = isCheck;
        this.fileType = fileType;
    }

    protected FileData(Parcel in) {
        name = in.readString();
        path = in.readString();
        time = in.readString();
        size = in.readString();
        type = in.readString();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<FileData> CREATOR = new Creator<FileData>() {
        @Override
        public FileData createFromParcel(Parcel in) {
            return new FileData(in);
        }

        @Override
        public FileData[] newArray(int size) {
            return new FileData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public MimeType getFileType() {
        return fileType;
    }

    public void setFileType(MimeType fileType) {
        this.fileType = fileType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(time);
        dest.writeString(size);
        dest.writeString(type);
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }
}

