package com.example.justbreathe.atti.Activity.Object;

public class LoginData {
    boolean korean;
    String name;

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

    public LoginData(boolean korean, String name) {

        this.korean = korean;
        this.name = name;
    }
}
