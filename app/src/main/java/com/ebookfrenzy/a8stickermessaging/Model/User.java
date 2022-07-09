package com.ebookfrenzy.a8stickermessaging.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String username;

    // stickerCount Map contains an ID and corresponding sent number
    //private Map<Integer, Integer> stickerCount;
    // history ArrayList contains sender's list
    //private ArrayList history;

    public User(String id, String username){
        this.id = id;
        this.username = username;
        //this.stickerCount = new HashMap<>();
        //this.history = new ArrayList();
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


    //public Map<Integer, Integer> getStickerCount(){ return stickerCount; }
    //public ArrayList getHistory() { return history; }
}
