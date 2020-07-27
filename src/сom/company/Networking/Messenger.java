package сom.company.Networking;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.nio.ByteBuffer;import static сom.company.Networking.Communicator.Write;

import java.nio.channels.DatagramChannel;




/**
 * главный класс для сетевого взаимодействия
 */
//Sends messages though the internet to the address
public class Messenger  {
    //do check connection

    public static void SendMessage(Message message, DatagramChannel channel, SocketAddress ADDRESS) {
        try {

            //create a byte array where we will store an object
            ByteBuffer msg = ByteBuffer.allocate(10*1024);
            msg.clear();
            msg.put(ByteConverter.convertToBytes(message));
            msg.flip();

            //send constructed message
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
                SendMessage(message, channel, ADDRESS);
            }

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Message ReceiveMessage(DatagramChannel channel){
        //create a byte array where we will store received object
        ByteBuffer buffer = ByteBuffer.allocate(10*1024);
        buffer.clear();

        try {
            //receive an object with its address and write it to the buffer and SocketAddress
            SocketAddress senderAddress = channel.receive(buffer);

            //if we received nothing then skip next step and return null
            if (senderAddress != null) {
                Message receivedMessage = (Message)ByteConverter.convertFromBytes(buffer.array());
                receivedMessage.senderAddress = senderAddress;
                if (receivedMessage.getWaitForResponse() && 1==0) {
                    SendMessage(new Message(NetworkingStatus.RECEIVED,null,false,null), channel, senderAddress);
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