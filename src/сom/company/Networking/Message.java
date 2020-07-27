package сom.company.Networking;

import сom.company.Collection.User;

import java.io.Serializable;
import java.net.SocketAddress;

/**
 * Отвечает за пересылку комманд
 */
public class Message implements Serializable {
    private Object content;
    private User user;
    private boolean waitForResponse;
     SocketAddress senderAddress;

    public Message(Object content,User user,boolean waitForResponse, SocketAddress senderAddress) {
        this.content = content;
        this.user=user;
        this.waitForResponse=waitForResponse;
        this.senderAddress = senderAddress;
    }

    public User getUser() {
        return user;
    }

    public Object getContent() {
        return content;
    }


    public SocketAddress getSenderAddress() {
        return senderAddress;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setSenderAddress(SocketAddress senderAddress) {
        this.senderAddress = senderAddress;
    }

    public void setWaitForResponse(boolean waitForResponse) {
        this.waitForResponse = waitForResponse;
    }

    public boolean getWaitForResponse() {
        return waitForResponse;
    }
}
