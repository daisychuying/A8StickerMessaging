package com.ebookfrenzy.a8stickermessaging.Model;

import java.util.HashMap;

public class User {

    private String id;
    private String username;

    private HashMap<String, Integer> stickerCount;

    public User(){}

    public User(String id, String username, HashMap<String, Integer> stickerCount){
        this.id = id;
        this.username = username;
        this.stickerCount = stickerCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, Integer> getStickerCount() {
        return stickerCount;
    }

    public void setStickerCount(HashMap<String, Integer> stickerCount) {
        this.stickerCount = stickerCount;
    }

    public void addStickerCount(String stickerId) {
        int oldCount = stickerCount.get(stickerId);
        stickerCount.put(stickerId, oldCount+ 1);
    }
}
