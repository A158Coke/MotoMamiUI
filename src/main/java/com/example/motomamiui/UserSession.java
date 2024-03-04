package com.example.motomamiui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserSession {
    private static UserSession instance; // 单例实例
    private final StringProperty userId = new SimpleStringProperty();

    // 私有构造函数，防止外部直接实例化
    private UserSession(String userId) {
        this.userId.set(userId);
    }

    // 获取UserSession的单例实例，如果实例不存在，就创建一个新的实例
    public static UserSession getInstance(String userId) {
        if (instance == null && userId != null) {
            instance = new UserSession(userId);
        }
        return instance;
    }

    // 清除UserSession的单例实例，用于注销用户
    public static void clearInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public String getUserId() {
        return userId.get();
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    // JavaFX属性通常用于绑定，如果不需要可以不提供
    public StringProperty userIdProperty() {
        return userId;
    }
}
