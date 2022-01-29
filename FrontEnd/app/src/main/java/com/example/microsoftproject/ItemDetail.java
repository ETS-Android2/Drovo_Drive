package com.example.microsoftproject;

public class ItemDetail {
    int size , imgId;
    String item_name;

    public ItemDetail(){

    }

    public ItemDetail(int size, int imgId, String item_name){
        this.imgId = imgId;
        this.item_name=item_name;
        this.size =size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
