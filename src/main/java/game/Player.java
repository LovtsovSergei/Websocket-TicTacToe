package game;

import javax.websocket.Session;
import java.io.IOException;

public class Player {

    private String mark;
    private Player otherPlayer;
    private Session session;

    public Player(String mark, Session session) {

        this.mark = mark;
        this.session = session;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public String getMark() {
        return mark;
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
