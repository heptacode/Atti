package com.example.justbreathe.atti.Activity.Object;

public class MainAC_Post {
    private String title, writer, content;
    private int like, ID;
    private boolean korean;
    private String image_url;


    public MainAC_Post(String title, String writer, String content, String image_url, int like, int ID,boolean korean) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.image_url = image_url;
        this.like = like;
        this.ID = ID;
        this.korean = korean;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isKorean() {
        return korean;
    }

    public void setKorean(boolean korean) {
        this.korean = korean;
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
