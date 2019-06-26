package app.atti.justbreathe.atti.Activity.Object;

public class Chat {
    private String timestamp;
    private String sender;
    private String message;

    public Chat(String timestamp, String sender, String message) {
        this.timestamp = timestamp;
        this.sender = sender;
        this.message = message;
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
