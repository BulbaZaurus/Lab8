package сom.company.Networking;

import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Networking.Servermachine.Server;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.nio.ByteBuffer;import static сom.company.Networking.Communicator.Write;

import java.nio.channels.DatagramChannel;
import java.util.TreeSet;


/**
 * главный класс для сетевого взаимодействия
 */

public class Messenger  {


    public static void SendMessage(Message message, DatagramChannel channel, SocketAddress ADDRESS, TreeSet<Ticket> ticketTreeSet) {
        try {


            ByteBuffer msg = ByteBuffer.allocate(10*1024);
            msg.clear();
            msg.put(ByteConverter.convertToBytes(message));
            msg.flip();


            channel.send(msg, ADDRESS);

            if(message.getWaitForResponse() && 1==0) {
                for(int i=0;i<100;i++){
                    //get a response that receiver received the message and only then proceed
                    Message receiveConfirmationMessage = ReceiveMessage(channel);

                    if (receiveConfirmationMessage != null) {
                        if (receiveConfirmationMessage.getContent() instanceof NetworkingStatus) {
                            if (receiveConfirmationMessage.getContent() == NetworkingStatus.RECEIVED) {
                                Write("Получаю подтвержение ");
                                return;
                            }
                        }else{
                            Write("ОШИБКА");
                        }
                    }
                    try {
                        Write("Wait "+i+" in thread "+Thread.currentThread().getName());
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        Write(e.getStackTrace().toString());
                    }
                }
                SendMessage(message, channel, ADDRESS, Controller.getTickets());
            }

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Message ReceiveMessage(DatagramChannel channel){

        ByteBuffer buffer = ByteBuffer.allocate(10*1024);
        buffer.clear();

        try {

            SocketAddress senderAddress = channel.receive(buffer);


            if (senderAddress != null) {
                Message receivedMessage = (Message)ByteConverter.convertFromBytes(buffer.array());
                receivedMessage.senderAddress = senderAddress;
                if (receivedMessage.getWaitForResponse() && 1==0) {
                    SendMessage(new Message(NetworkingStatus.RECEIVED,null,false,null,Controller.getTickets()), channel, senderAddress, Controller.getTickets());
                }
                return receivedMessage;
            }
        }catch(PortUnreachableException ex){
            Write("Не могу пожключится.");
            return null;
        } catch (IOException ex) {
            Write("I/O error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }


}