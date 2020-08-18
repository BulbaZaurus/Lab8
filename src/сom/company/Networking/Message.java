package сom.company.Networking;

import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.Networking.Servermachine.Server;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.TreeSet;

/**
 * Отвечает за пересылку комманд
 */
public class Message implements Serializable {
    private Object content;
    private User user;
    private boolean waitForResponse;
     SocketAddress senderAddress;
    TreeSet<Ticket> tickets;

    public TreeSet<Ticket> getTickets() {
        return tickets;
    }

    public Message(Object content, User user, boolean waitForResponse, SocketAddress senderAddress, TreeSet<Ticket> tickets) {
        this.content = content;
        this.user=user;
        this.waitForResponse=waitForResponse;
        this.senderAddress = senderAddress;
        this.tickets= Controller.getTickets();
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

    public boolean getWaitForResponse() {
        return waitForResponse;
    }
}
