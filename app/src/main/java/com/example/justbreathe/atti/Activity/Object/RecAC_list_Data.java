package com.example.justbreathe.atti.Activity.Object;

import java.util.ArrayList;

public class RecAC_list_Data {
    ArrayList<String> images;
    String etc, name, time, day, url, desc;

    public RecAC_list_Data(ArrayList<String> images, String etc, String name, String time, String day, String url, String desc) {
        this.images = images;
        this.etc = etc;
        this.name = name;
        this.time = time;
        this.day = day;
        this.url = url;
        this.desc = desc;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
