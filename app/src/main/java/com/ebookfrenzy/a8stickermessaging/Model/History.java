package com.ebookfrenzy.a8stickermessaging.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class History {

    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private int sticker;
    private long timeStamp;
    //
    private boolean notified;

    public History(){}

    public History(String senderId, String senderName, String receiverId, String receiverName, int sticker, long timeStamp, boolean notified) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.sticker = sticker;
        this.timeStamp = timeStamp;
        this.notified = notified;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getSticker() {
        return sticker;
    }

    public void setSticker(int sticker) {
        this.sticker = sticker;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isNotified() {return notified;}

    public void setNotified(boolean notified) {this.notified = notified;}

    public String convertTimestamp() {
        Timestamp ts = new Timestamp(this.timeStamp);
        Date date = new Date(ts.getTime());
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault());
        return format.format(date);
    }
}
