package websocket;

import game.Field;
import game.Player;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@ServerEndpoint("/game")
public class WsServer {

    private Player player;
    private final static List<Player> PLAYERS = new CopyOnWriteArrayList<>();
    private final static Field field = new Field();

    @OnOpen
    public void onOpen(Session session) {

        System.out.println("Open Connection ...");

        if (PLAYERS.isEmpty()) {
            player = new Player("X", session);
            PLAYERS.add(player);
            player.sendMessage("PlayerX");
        } else if (PLAYERS.size() == 1) {
            player = new Player("O", session);
            player.setOtherPlayer(PLAYERS.get(0));
            PLAYERS.get(0).setOtherPlayer(player);
            PLAYERS.add(player);
            player.sendMessage("PlayerO");
            field.gameStart();
        }
    }

    @OnClose
    public void onClose() {
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {
        if (message.equals("Start")) {
            player.sendMessage("Start");
            player.getOtherPlayer().sendMessage("Start");
            if (player.getMark().equals("X")) {
                player.sendMessage("YourTurn");
            } else player.getOtherPlayer().sendMessage("YourTurn");
        } else {
            int move = getMove(message);
            movesHandler(move);
        }
    }

    private int getMove(String move) {
        return Integer.parseInt(move);
    }

    private void movesHandler(int move) {
        if (!Field.cellIsEmpty(move)) {
            player.sendMessage("NotEmptyCell");
            return;
        }
        Field.setMove(move, player.getMark());
        player.getOtherPlayer().sendMessage("OppMove:" + move);
        if (!Field.gameFinished()) {
            player.getOtherPlayer().sendMessage("YourTurn");
        }
        if (Field.isWin()) {
            player.sendMessage("Win");
            player.getOtherPlayer().sendMessage("Lose");
        }
        if (Field.isDraw()) {
            player.sendMessage("Draw");
            player.getOtherPlayer().sendMessage("Draw");
        }
    }
}
