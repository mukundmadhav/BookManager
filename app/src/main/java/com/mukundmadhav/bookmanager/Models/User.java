package com.mukundmadhav.bookmanager.Models;
public class User {
    private String title;
    private String userId;
    private String userPic;
    private String userName;

    public User(String title, String userId, String userPic, String userName) {
        this.title = title;
        this.userId = userId;
        this.userPic = userPic;
        this.userName = userName;
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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