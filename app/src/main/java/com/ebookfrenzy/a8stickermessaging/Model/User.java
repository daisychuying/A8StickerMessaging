package com.ebookfrenzy.a8stickermessaging.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String username;

    // stickerCount Map contains an ID and corresponding sent number
    private Map<String, Integer> stickerCount;
    // history ArrayList contains sender's list
//    private ArrayList history;

    public User(){}

    public User(String id, String username){
        this.id = id;
        this.username = username;
        this.stickerCount = new HashMap<>();
        for (int i = 0; i < new StickerMap().size(); i++){
            stickerCount.put(String.valueOf(i), 0);
        }
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
