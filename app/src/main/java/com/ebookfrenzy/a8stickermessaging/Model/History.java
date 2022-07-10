package com.ebookfrenzy.a8stickermessaging.Model;

public class History {

    private String sender;
    private String receiver;
    private String stickerId;
    private String time;

    public History(){}

    public History(String sender, String receiver, String stickerId, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.stickerId = stickerId;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
