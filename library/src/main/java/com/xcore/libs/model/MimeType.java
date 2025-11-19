package com.xcore.libs.model;

public enum MimeType {

    IMAGE("image/*"),
    AUDIO("audio/*"),
    VIDEO("video/*"),
    TEXT("text/plain"),
    HTML("text/html"),
    PDF("application/pdf"),
    APPLICATION("application/vnd.android.package-archive"),
    UNKNOWN("");

    private final String mimeString;

    MimeType(String mimeString) {
        this.mimeString = mimeString;
    }

    public String getMimeString() {
        return mimeString;
    }

    /**
     * 检查指定的MIME类型是否属于当前类型
     */
    public boolean matches(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return false;
        }
        // 处理通配符类型匹配
        if (this == IMAGE) {
            return mimeType.startsWith("image/");
        }
        if (this == AUDIO) {
            return mimeType.startsWith("audio/");
        }
        if (this == VIDEO) {
            return mimeType.startsWith("video/");
        }
        // 精确匹配其他类型
        return mimeType.equals(this.mimeString);
    }

    /**
     * 根据MIME字符串获取对应的枚举实例
     */
    public static MimeType fromMimeString(String mimeString) {
        if (mimeString == null || mimeString.isEmpty()) {
            return UNKNOWN;
        }
        for (MimeType type : values()) {
            if (type.matches(mimeString)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
