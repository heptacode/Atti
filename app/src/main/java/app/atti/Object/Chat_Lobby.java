package app.atti.Object;

public class Chat_Lobby {
    String chatName_Eng;
    String chatName_Kor;
    String recent_chat;
    String recent_date;
    String recent_time;

    public Chat_Lobby() {
    }

    public Chat_Lobby(String chatName_Eng, String chatName_Kor, String recent_chat, String recent_date,String recent_time) {
        this.chatName_Eng = chatName_Eng;//방 이름 구하려고
        this.chatName_Kor = chatName_Kor;//사람 이름
        this.recent_chat = recent_chat;
        this.recent_date = recent_date;
        this.recent_time= recent_time;
    }

    public String getChatName_Eng() {
        return chatName_Eng;
    }

    public void setChatName_Eng(String chatName_Eng) {
        this.chatName_Eng = chatName_Eng;
    }

    public String getChatName_Kor() {
        return chatName_Kor;
    }

    public void setChatName_Kor(String chatName_Kor) {
        this.chatName_Kor = chatName_Kor;
    }

    public String getRecent_chat() {
        return recent_chat;
    }

    public void setRecent_chat(String recent_chat) {
        this.recent_chat = recent_chat;
    }

    public String getRecent_date() {
        return recent_date;
    }

    public void setRecent_date(String recent_date) {
        this.recent_date = recent_date;
    }

    public String getRecent_time() {
        return recent_time;
    }

    public void setRecent_time(String recent_time) {
        this.recent_time = recent_time;
    }
}
