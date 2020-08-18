package сom.company.Networking.Servermachine;

import java.io.*;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.*;


import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.GUI.MainForm;
import сom.company.Networking.DBSavingProtocol;
import сom.company.Networking.Message;
import сom.company.Networking.Messenger;
import сom.company.Collection.command.CollectionCommand;


/**
 * @author Dlinnый
 * @version 3.0
 */
public class Server extends Messenger {
    private static int core = Runtime.getRuntime().availableProcessors();
    private static DatagramChannel channel;
    private static Connection connection;
    private static Statement statement;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String NAME = "postgres";
    private static final String PASS = "root";
    static Controller controller ;
    static TreeSet<Ticket> treeSet;
    public static TreeSet<Ticket> getTreeSet() {
        return treeSet;
    }
    private static ExecutorService FTPProcessing= Executors.newFixedThreadPool(core);
    private static ForkJoinPool forkJoinPool=new ForkJoinPool();
    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void main(String[] args) {
        controller=new Controller();
        treeSet= Controller.getTickets();
        if (args.length != 1) {
            System.out.println("Syntax: сom.company.сom.company.Networking.Networking.Servermachine.Server <port>");
            return;
        }
        DBConnection();
        controller.main(new String[]{"savePath.xml", "true"},connection,statement);
        InitConnection(Integer.parseInt(args[0]));
        Process();
    }
    /**
     * Иннициализация соеденения
     * @param port Порт
     */
    public static void InitConnection(int port) {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port));
            Thread.sleep(500);
        } catch (UnknownHostException ex) {
            System.out.println("Host error: " + ex.getMessage());
            InitConnection(port);
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
            InitConnection(port);
        } catch (IOException ex) {
            System.out.println("I/O error when initializing connection.");
            InitConnection(port);
        } catch (InterruptedException e) {
            e.printStackTrace();
            InitConnection(port);
        }
        System.out.println("Server has started on port "+port);

    }
    /**
     *
     * Отвечает за входящие сообщения со стороны клиента
     */
    private static void Process() {
        while(true){
            Message receivedMessage=ReceiveMessage(channel);
           controller.setMessage(receivedMessage);
            boolean Someboolean=receivedMessage!=null && receivedMessage.getContent()!=null;
            if(Someboolean) {
                FTPProcessing.execute(new ServerProcessThread(receivedMessage,connection,channel,forkJoinPool));
            }
        }
    }

    /**
     * Отвечает за подключение к БД

     */
    private static void DBConnection(){
        System.out.println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,NAME, PASS);
            statement = connection.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
