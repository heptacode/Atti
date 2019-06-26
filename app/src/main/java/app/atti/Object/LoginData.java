package app.atti.Object;

public class LoginData {
    private boolean korean;
    private String name;
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
