package com.ebookfrenzy.a8stickermessaging.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;

    // stickerCount Map contains an ID and corresponding sent number
    private Map<Integer, Integer> stickerCount;
    // history ArrayList contains sender's list
    private ArrayList history;

    public User(String username){
        this.username = username;
        this.stickerCount = new HashMap<>();
        this.history = new ArrayList();
    }

    public String getUsername(){ return username; }
    public Map<Integer, Integer> getStickerCount(){ return stickerCount; }
    public ArrayList getHistory() { return history; }
}
