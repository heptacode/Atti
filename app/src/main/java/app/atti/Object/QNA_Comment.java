package app.atti.Object;

public class QNA_Comment {
    String name;
    String email;
    String date;
    boolean korean;
    String comment;

    public QNA_Comment(String name, String email, String date, boolean korean, String comment) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.korean = korean;
        this.comment = comment;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isKorean() {
        return korean;
    }

    public void setKorean(boolean korean) {
        this.korean = korean;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
