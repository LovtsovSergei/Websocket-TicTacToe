package websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer  {
    @OnOpen
    public void onOpen() {
        System.out.println("Open Connection ...");
    }

    @OnClose
    public void onClose() {
        System.out.println("Close Connection ...");
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnMessage
    public String onMessage(String message) {
        System.out.println("Message from the client: " + message);
        String echoMsg = "Echo from the server : " + message;
        return echoMsg;
    }

}
