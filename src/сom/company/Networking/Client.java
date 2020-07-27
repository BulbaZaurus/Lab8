package сom.company.Networking;






import сom.company.Collection.Controller;
import сom.company.Collection.User;
import сom.company.Collection.UserStatus;
import сom.company.Collection.command.*;
import сom.company.GUI.LoginForm;

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
    static User user;

    public static User getUser() {
        return user;
    }
    private String host;
    private String port;
    public static SocketAddress ADDRESS;
    private static DatagramChannel channel;
    private static Scanner scanner;
    private InputStream inputStream;


    public static void setUser(User user) {
        Client.user = user;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    private static List<String> executed_scripts= new ArrayList<String>();

    public static void main(String[] args) {
        user = new User("root","root");
        if (args.length != 2) {
            System.out.println("Syntax: сom.company.сom.company.Networking.Networking.Client <hostname> <port>");
            return;
        }

        InitReader();
        ConnectToServer(args[0], args[1]);
        //StartWindow.StartClientGUI();
        LoginForm.StartLogin();

        //Userinitilization();
        Process();

    }







    /**
     * Фактически метод обеспечивает работу класса Client
     * Проверяет комманды на соответсвие с различными командами
     */



    public static void Process() {
        while (true) {
            //create a command that will be sent to the server
            Controller.PrintWaitingInfo();

            CollectionCommand command = CommandBuilder.Build(Read());
            if(command==null){
                continue;
            }

            if(OverrideCommandsClient(command)){
                continue;
            }

            try {
                Thread.sleep(500);
            }catch(InterruptedException e){
                Write(e.getStackTrace().toString());
            }
            //receive the response
            Message receivedMessage = ReceiveMessage(channel);

            //output the response
            if(receivedMessage.getContent()==null){
                continue;
            }
            WriteStringArray((String[])(receivedMessage.getContent()));
            //wait a second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static boolean OverrideCommandsClient(CollectionCommand collectionCommand){
        if(collectionCommand instanceof CollectionCommand_Save){
            Write("Нельзя использовать комманду save на клиенте ");
            return true;
        }

        try {
            if (collectionCommand instanceof CollectionCommand_Execute_script) {
                if(executed_scripts.contains(((CollectionCommand_Execute_script) collectionCommand).file_path)){
                    //recursion
                    Write("ТЫ не пройдешь!");
                    Write("Тип...рекурсия и всё такое");
                    InitReader();
                    return true;
                }
                executed_scripts.add(((CollectionCommand_Execute_script) collectionCommand).file_path);
                InputStream is = new FileInputStream(((CollectionCommand_Execute_script) collectionCommand).file_path);
                InitReader(is);
                return true;
            }
        }catch(FileNotFoundException ex){
            Write("Файл не найден");
        }

        if(collectionCommand instanceof CollectionCommand_Update_id){
            SendMessage(new Message(new ShowById(((CollectionCommand_Update_id) collectionCommand).id),user,true,null),channel,ADDRESS);

            try {
                Thread.sleep(500);
            }catch(InterruptedException e){
                Write(e.getStackTrace().toString());
            }
            //receive the response
            Message receivedMessage = ReceiveMessage(channel);

            //output the response
            if(receivedMessage!=null) {
                String[] output = (String[])(receivedMessage.getContent());
                if(output.length==0){
                    Write("Билет с таким id не найден.");
                    return true;
                }
                Write(Integer.toString(output.length));
                for (String str: output) {
                    Write(str);
                }
            }
            ((CollectionCommand_Update_id) collectionCommand).Prepare();
        }

        //SendMessage(new Message(command,user,true,null), channel, ADDRESS);

        if(collectionCommand instanceof CollectionCommand_Exit){
            collectionCommand.Execute(null,null,"",new boolean[]{false},null,user);
        }
        SendMessage(new Message(collectionCommand,user,true,null), channel, ADDRESS);
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
        Message msg=new Message(new LogIn(),user,true,null);
        SendMessage(msg,channel,ADDRESS);
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
        if(receivedMessage.getContent() == UserStatus.LOGGED_IN){
            Write("Вход в систему успешно завершен");
            System.out.println(user.toString());
            return true;
        }else{
            Write("Мы не обнаружили никого,кто бы подходил по этим параметрам.");
            return false;
        }

    }

    public static boolean RegistrationUser(User user){
        Message msg = new Message(new Registration(),user,true,null);
        SendMessage(msg,channel,ADDRESS);
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
    public static void Userinitilization(){
        User user = new User();
        User secondUser=new User();
        System.out.println("Владыка,приветсвую тебя!");
        System.out.println("Если у вас отсутсвует аккаунт то пропишите registration иначе login ");
        String userAction = new Scanner(System.in).nextLine();
        switch (userAction){
            case "login":
                String name =User.inputName();
                String password = User.inputPassword();
                user.setName(name);
                user.setPassword(password);

                Message msg=new Message(new LogIn(),user,true,null);
                SendMessage(msg,channel,ADDRESS);
                try {
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    Write(e.getStackTrace().toString());
                }
                Message receivedMessage = ReceiveMessage(channel);
                if(receivedMessage==null){
                    Write("Сервер не отвечает.Попробуйте позже");
                    Userinitilization();
                    return;
                }
                if(receivedMessage.getContent() == UserStatus.LOGGED_IN){
                    Write("Вход в систему успешно завершен");
                    System.out.println(user.toString());
                }else{
                    Write("Мы не обнаружили никого,кто бы подходил по этим параметрам.");
                    Userinitilization();
                }
                break;
            case "registration":
                String nameS =User.inputName();
                String passwordS = User.inputPassword();


                user=new User(nameS,passwordS);


                msg = new Message(new Registration(),user,true,null);
                SendMessage(msg,channel,ADDRESS);
                try {
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    Write(e.getStackTrace().toString());
                }
                receivedMessage = ReceiveMessage(channel);
                if(receivedMessage==null){
                    Write("Сервер не отвечает.Попробуйте позже");
                    Userinitilization();
                    return;
                }
                if(receivedMessage.getContent() == UserStatus.SIGNED_IN){
                    Write("Регистрация выполнена успешно");
                }else{
                    Write("Владыка,это имя уже занято.Повторите процедуру регистрации ");
                    Userinitilization();
                }
                break;
            default:
                System.out.println("Я не понимаю,что ты хочешь от меня!");
                System.out.println("Начинаю процедуру заново ");
                System.out.println("");
                Userinitilization();
        }
    }


}