package сom.company.Networking.Servermachine;

import сom.company.Networking.Message;
import сom.company.Networking.Messenger;


import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.util.concurrent.ForkJoinPool;

public class ServerThreadSender implements Runnable {
    private Message message;
    private DatagramChannel channel;
    private SocketAddress senderAddress;
    private ForkJoinPool forkJoinPool;
    private Connection connection;
    public ServerThreadSender(Message message, DatagramChannel channel,SocketAddress senderAddress){
        this.message = message;
        this.channel = channel;
        this.senderAddress=senderAddress;
    }
    @Override
    public void run(){
        Messenger.SendMessage(message,channel,senderAddress);
    }
}
