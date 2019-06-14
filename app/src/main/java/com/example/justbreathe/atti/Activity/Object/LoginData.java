package com.example.justbreathe.atti.Activity.Object;

public class LoginData {
    private boolean korean;
    private String name;
    private String profile_url;
    public boolean isKorean() {
        return korean;
    }

    public void setKorean(boolean korean) {
        this.korean = korean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginData(boolean korean, String name,String profile_url) {
        this.profile_url = profile_url;
        this.korean = korean;
        this.name = name;
    }
}
