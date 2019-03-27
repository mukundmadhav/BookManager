package com.mukundmadhav.bookmanager.Models;

import com.google.firebase.database.ServerValue;

public class Book {

    private String title, price, picture, userId, userPic, postKey;
    private Object timeStamp;


    public Book(String title, String price, String picture, String userId, String userPic) {
        this.title = title;
        this.price = price;
        this.picture = picture;
        this.userId = userId;
        this.userPic = userPic;
        this.timeStamp = ServerValue.TIMESTAMP;;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
