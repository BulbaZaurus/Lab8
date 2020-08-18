package сom.company.Networking;

import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.Collection.UserStatus;
import сom.company.Collection.command.*;
import сom.company.GUI.LoginForm;
import сom.company.Networking.Servermachine.Server;

import javax.swing.*;

import static сom.company.Networking.Communicator.Write;
import static сom.company.Networking.Communicator.WriteStringArray;


import java.io.*;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.util.*;

/**
 * @author }{отт@бь)ч
 * @version 1.8
 */
public class Client extends Messenger {
    public  static  User user;

    static TreeSet<Ticket> treeSet;

    public static TreeSet<Ticket> getTreeSet() {
        return treeSet;
    }

    static ArrayList<Long> idList;

    public static ArrayList<Long> getIdList() {
        return idList;
    }

    public static User getUser() {
        return user;
    }

    public static SocketAddress ADDRESS;
    private static DatagramChannel channel;
    private static Scanner scanner;


    public static void setUser(User user) {
        Client.user = user;
    }

    public static List<String> executed_scripts= new ArrayList<String>();

    public static void main(String[] args) {

        
        if (args.length != 2) {
            System.out.println("Syntax: сom.company.сom.company.Networking.Networking.Client <hostname> <port>");
            return;
        }

        InitReader();
        ConnectToServer(args[0], args[1]);
        Locale.setDefault(new Locale("pl","PL"));
        System.out.println(Locale.getDefault());
        SwingUtilities.invokeLater(()->LoginForm.StartLogin());


    }







    /**
     * Фактически метод обеспечивает работу класса Client
     * Проверяет комманды на соответсвие с различными командами
     */



    public static void Process(CollectionCommand command) {

    Controller.PrintWaitingInfo();
        Controller.PrintWaitingInfo();
        if(command==null){

        }

        if(OverrideCommandsClient(command)){

        }

        try {
            Thread.sleep(500);
        }catch(InterruptedException e){
            Write(e.getStackTrace().toString());
        }

        Message receivedMessage = ReceiveMessage(channel);


       if(receivedMessage.getContent()==null){
           System.out.println("Тут происходит ошибка,которая не мешает вам");
        }
        WriteStringArray((String[])(receivedMessage.getContent()));

        try {
            Thread.sleep(500); //было 1000
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        treeSet=receivedMessage.getTickets();

        System.out.println(idList);
        System.out.println("Размер коллекции это "+ treeSet.size());

    }

    private static boolean OverrideCommandsClient(CollectionCommand collectionCommand){
        if(collectionCommand instanceof CollectionCommand_Exit){
            collectionCommand.Execute(null,null,"",new boolean[]{false},null,user);
        }

        SendMessage(new Message(collectionCommand,user,true,null,Controller.getTickets()), channel, ADDRESS,treeSet);
        return false;
    }


    /**
     * Иннициализация соеденения
     * @param hostname Хост
     * @param port Порт
     */
    public static void ConnectToServer(String hostname, String port) {
        try {
            int Port = Integer.parseInt(port);
            ADDRESS = new InetSocketAddress(hostname, Port);

            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().connect(ADDRESS);

            Thread.sleep(300);
            return;
        } catch (UnknownHostException ex) {
            Write("Host error: " + ex.getMessage());
        } catch (SocketException ex) {
            Write("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            Write("I/O error when initializing connection.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConnectToServer(hostname, port);
    }

    /**
     * Отвечает за чтение
     * @return String
     */
    public static String Read(){
        if(scanner.hasNext()){
            String s = scanner.nextLine();
            return s;
        }else{
            InitReader();
        }
        return scanner.nextLine();
    }

    public static void InitReader(){
        executed_scripts=new ArrayList<String>();
        InitReader(System.in);
    }

    public static void InitReader(InputStream input){
        scanner = new Scanner(input);
    }


    public static boolean LoginUser(User user){
        Message msg=new Message(new LogIn(),user,true,null, Server.getTreeSet());
        SendMessage(msg,channel,ADDRESS,treeSet);
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            Write(e.getStackTrace().toString());
        }
        Message receivedMessage = ReceiveMessage(channel);
        if(receivedMessage==null){
            Write("Сервер не отвечает.Попробуйте позже");
            return false;
        }
        else if(receivedMessage.getContent() == UserStatus.LOGGED_IN){
            Write("Вход в систему успешно завершен");
            System.out.println(user.toString());
            return true;
        }else{
            Write("Мы не обнаружили никого,кто бы подходил по этим параметрам.");
            return false;
        }

    }

    public static boolean RegistrationUser(User user){
        Message msg = new Message(new Registration(),user,true,null,Controller.getTickets());
        SendMessage(msg,channel,ADDRESS,treeSet);
        try {
            Thread.sleep(500);
        }catch(InterruptedException e){
            Write(e.getStackTrace().toString());
        }
        Message receivedMessage = ReceiveMessage(channel);
        if(receivedMessage==null){
            Write("Сервер не отвечает.Попробуйте позже");
            return false;
        }
        if(receivedMessage.getContent() == UserStatus.SIGNED_IN){
            Write("Регистрация выполнена успешно");
            return true;
        }else{
            Write("Владыка,это имя уже занято.Повторите процедуру регистрации ");
            return false;

        }

    }



}