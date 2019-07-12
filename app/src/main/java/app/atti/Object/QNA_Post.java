package app.atti.Object;

public class QNA_Post {
    private String title, writer, content, date, ID, writer_email;
    private int like;
    private boolean korean, i_like;
    private String image_url;


    public QNA_Post(String title, String date, String writer, String content, String image_url, int like, String ID, boolean korean, boolean i_like, String writer_email) {
        this.title = title;
        this.date = date;
        this.writer = writer;
        this.content = content;
        this.image_url = image_url;
        this.like = like;
        this.ID = ID;
        this.korean = korean;
        this.i_like = i_like;
        this.writer_email = writer_email;
    }

    public String getWriter_email() {
        return writer_email;
    }

    public void setWriter_email(String writer_email) {
        this.writer_email = writer_email;
    }

    public boolean isI_like() {
        return i_like;
    }

    public void setI_like(boolean i_like) {
        this.i_like = i_like;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
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
