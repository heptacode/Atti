package com.example.justbreathe.atti.Activity.Object;

public class MainAC_Post {
    private String profile_url="", title, writer, content, image_url="";
    private int like,ID;
    public MainAC_Post(String profile_url, String title, String writer, String content, String image_url,int like, int ID) {
        this.profile_url = profile_url;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.image_url = image_url;
        this.like = like;
        this.ID = ID;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}