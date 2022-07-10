package com.ebookfrenzy.a8stickermessaging.Model;

public class Sticker {
    private Integer id;
    private String stickerSource;

    private Sticker(){}

    public Sticker(Integer id, String stickerSourceL){
        this.id = id;
        this.stickerSource = stickerSourceL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStickerSource() {
        return stickerSource;
    }

    public void setStickerSource(String stickerSource) {
        this.stickerSource = stickerSource;
    }
}
