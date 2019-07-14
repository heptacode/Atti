package app.atti.Object;

public class Chat {
    private String timestamp;
    private String sender;
    private String message;
    private String date;
    private boolean prev;
    public Chat() {
    }



    public Chat(String message, String sender, String timestamp, String date,boolean prev) {
        this.timestamp = timestamp;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.prev=prev;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
