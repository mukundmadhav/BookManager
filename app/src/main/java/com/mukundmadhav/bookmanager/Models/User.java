package com.mukundmadhav.bookmanager.Models;
public class User {
    private String title, userId, userPic;

    public User(){

    }

    public User(String title, String userId, String userPic) {
        this.title = title;
        this.userId = userId;
        this.userPic = userPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}