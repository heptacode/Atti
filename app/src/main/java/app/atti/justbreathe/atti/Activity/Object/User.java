package app.atti.justbreathe.atti.Activity.Object;

public class User {
    private boolean korean;
    private String name;
    private String email;
    private String passwd;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public User(boolean korean, String name, String email, String passwd) {

        this.korean = korean;
        this.name = name;
        this.email = email;
        this.passwd = passwd;
    }
}
