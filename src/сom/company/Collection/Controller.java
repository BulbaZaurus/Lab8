package сom.company.Collection;


import сom.company.Collection.command.*;
import сom.company.GUI.AddForm;
import сom.company.Networking.DBSavingProtocol;
import сom.company.Networking.Message;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author }{отт@бь)ч
 * @version  1.79
 */

public class Controller implements Serializable{

    private static final long serialVersionUID = 1403L;
        static TreeSet<Ticket> tickets = new TreeSet<Ticket>();


    public static TreeSet<Ticket> getTickets() {
        return tickets;
    }

    static Scanner[] in = new Scanner[]{new Scanner(System.in)};
        static String savePath;
        static boolean[] readingfromsystem = new boolean[]{true};
        static boolean isCommandsFromOutside;
        static Connection connection;
        static Statement statement;
        static  Message message;

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void PrintWaitingInfo() {

            System.out.println();
            System.out.println("Ожидаю ввода команды...");

    }

    public void  setMessage(Message message){
            this.message=message;
        }

    public static List<String> executed_scripts = new ArrayList();

    /**
     * комментариев не требует
     * @param args параметр
     */
    public  void main(String[] args,Connection con,Statement st) {
        connection=con;
        statement=st;

        LocalDateTime dateTime = LocalDateTime.now();
        in = new Scanner[]{new Scanner(System.in)};
        readingfromsystem = new boolean[]{true};
        tickets= DBSavingProtocol.LoadCollection(connection,statement);


        if (args.length > 0) {
            savePath = args[0];
            if (args.length > 1) {
                isCommandsFromOutside = args[1].equals("true");
            }
        } else {
            System.out.print("Давным давно....на одном допе.... ");


        }

        while (!isCommandsFromOutside) {
            //read from keyboard (lab 5)

            ExecuteCommand(CommandBuilder.Build(read_string()),null);
        }
    }
    public  static void ExecuteCommand(CollectionCommand commandToExecute, User user){
        if(commandToExecute==null){
            return;
        }
        tickets= DBSavingProtocol.LoadCollection(connection,statement);
        commandToExecute.Execute(tickets,in,savePath, readingfromsystem,executed_scripts,user);//add



    }

  private static CollectionCommand command(ActionEvent event){
        if(event.getSource()==AddForm.frame.getButt()){
            System.out.println("Япи");
            return new CollectionCommand_Add(AddForm.frame.getBufTicket());
        }
      return null;


  }




    /**
     * Метод для чтения скриптов
     * @return String
     */
    public  String read_string(){
        try {
            String nextLine = in[0].nextLine();
            if(!readingfromsystem[0]){
                if(nextLine.contains("execute_script")){
                    if(nextLine.split(" ").length>1){
                        String script_name = nextLine.split(" ")[1];
                        if(executed_scripts.contains(script_name)){
                            if(!executed_scripts.get(executed_scripts.size() - 1).equals(script_name)){
                                System.out.println("Рукурсия alert! ");
                                Exit(true);
                            }
                        }
                        executed_scripts.add(script_name);
                    }
                }
                if(!in[0].hasNext()){
                    executed_scripts=new ArrayList();
                    in[0] = new Scanner(System.in);
                    readingfromsystem[0]=true;
                }
            }else{
                executed_scripts=new ArrayList();
                in[0] = new Scanner(System.in);
                readingfromsystem[0]=true;
            }
            return nextLine;
        }catch(java.util.NoSuchElementException e){

            in[0] = new Scanner(System.in);
            readingfromsystem[0]=true;
            return in[0].nextLine();
        }catch(Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            Exit(true);
        }
        return null;
    }

    public  void Exit(boolean force){
        new CollectionCommand_Exit(force).Execute(tickets,in,savePath, readingfromsystem,executed_scripts,null);
    }






    }




