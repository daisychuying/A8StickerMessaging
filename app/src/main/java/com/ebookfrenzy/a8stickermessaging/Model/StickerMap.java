package com.ebookfrenzy.a8stickermessaging.Model;

import com.ebookfrenzy.a8stickermessaging.R;

import java.util.HashMap;

public class StickerMap {
    public HashMap<Integer, Integer> stickerMap = new HashMap<>();

    public StickerMap(){
        stickerMap.put(1, R.drawable.ic_ok_hand);
        stickerMap.put(2, R.drawable.ic_pepe_cry);
        stickerMap.put(3, R.drawable.ic_sea_hello);
        stickerMap.put(4, R.drawable.ic_cat_hi);
        stickerMap.put(5, R.drawable.ic_sticker_dog_awkward);
        stickerMap.put(6, R.drawable.ic_wow_happy);
    }

    public int getStickerId(Integer id){
        return stickerMap.get(id);
    }
}