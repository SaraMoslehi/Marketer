package com.example.sara.marketer.model;


import com.google.gson.Gson;

/**
 * Created by Raad on 6/13/17.
 */

public class User {
    String mobile;
    String username;
    String jwtToken;

    public static User mInstance;

    public String getJwtToken() {
        return jwtToken;
    }
//    boolean isRegisterd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


//    public boolean isRegisterd() {
//        return isRegisterd;
//    }
//
//    public void setRegisterd(boolean registerd) {
//        isRegisterd = registerd;
//    }


    public User() {
    }

    public User(String mobile, String username, String jwtToken) {
        this.mobile = mobile;
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public synchronized static User getInstance() {
        if (mInstance == null) {
//            String user = MyApplication.MY_SHARED_PREFRENCES.getString(SharedprefrencedUtil.USER_KEY, null);
            Gson gson = new Gson();
//            mInstance = gson.fromJson(user, User.class);

        }
        return mInstance;
    }


    public static void setUser(User user) {
        mInstance = user;

    }


}
