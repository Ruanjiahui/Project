package com.ruan.project.Moudle;

import com.example.administrator.ui_sdk.Applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class User {

    private String userID = null;
    private String userName = null;
    private String userPassword = null;
    private String userSex = null;
    private String userBirthday = null;
    private String userHeight = null;
    private String userWeight = null;
    private String userPhone = null;
    private String userLogin = null;

    private static User user = null;

    public static User getInstance() {
        if (user == null)
            user = new User();
        return user;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public static void toModel(ArrayList<Map<String, String>> list) {
        user = getInstance();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if ("true".equals(list.get(i).get("userLogin"))) {
                    user.setUserBirthday(list.get(i).get("userBirthday"));
                    user.setUserHeight(list.get(i).get("userHeight"));
                    user.setUserID(list.get(i).get("userID"));
                    user.setUserLogin(list.get(i).get("userLogin"));
                    user.setUserName(list.get(i).get("userName"));
                    user.setUserPassword(list.get(i).get("userPassword"));
                    user.setUserPhone(list.get(i).get("userPhone"));
                    user.setUserSex(list.get(i).get("userSex"));
                    user.setUserWeight(list.get(i).get("userWeight"));
                }
            }
        }
    }
}
