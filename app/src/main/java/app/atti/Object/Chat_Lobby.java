package app.atti.Object;

public class Chat_Lobby {
    String chatName_Eng;
    String chatName_Kor;

    public Chat_Lobby() {
    }

    public Chat_Lobby(String chatName_Eng, String chatName_Kor) {
        this.chatName_Eng = chatName_Eng;//방 이름 구하려고
        this.chatName_Kor = chatName_Kor;//사람 이름
    }

    public String getchatName_Eng() {
        return chatName_Eng;
    }

    public void setchatName_Eng(String chatName_Eng) {
        this.chatName_Eng = chatName_Eng;
    }

    public String getchatName_Kor() {
        return chatName_Kor;
    }

    public void setchatName_Kor(String chatName_Kor) {
        this.chatName_Kor = chatName_Kor;
    }
}
