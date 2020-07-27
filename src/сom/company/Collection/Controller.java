package сom.company.Collection;


import сom.company.Collection.command.*;
import сom.company.Networking.DBSavingProtocol;
import сom.company.Networking.Message;


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
            System.out.print("Давным давно....на одном допе.... "); //если файл существует ,то просто добавь чтение
            //savePath=read_string();

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
  private static CollectionCommand command(String input){
      String[] args = input.split(" ");
      switch (args[0]) {
          case ("help"):

              return new CollectionCommand_Help();
          case ("info"):

              return new CollectionCommand_Info();
          case ("show"):

              return new CollectionCommand_Show();

          case ("add"):

              return new CollectionCommand_Add(Ticket.TicketBuilder());

          case ("update_id"):

              if (args.length == 2) {
                  try {
                      return new CollectionCommand_Update_id(Integer.parseInt(args[1]));
                  } catch (NumberFormatException e) {
                      System.out.println(args[1] + " Воу воу палехче.Слишком большой ");
                  } catch (Exception var8) {
                      System.out.println("Эксепшон");
                  }
              } else {
                  System.out.println("Ожидался аргумент " + (args.length - 1));

              }
              break;
          case ("remove_by_id"):

              if (args.length == 2) {
                  try {
                      return new CollectionCommand_Remove_by_id(Long.parseLong(args[1]));
                  } catch (NumberFormatException var5) {
                      System.out.println(args[1] + " Воу воу палехче.Слишком большой");
                  } catch (Exception var6) {
                      System.out.println("Эксепшон");
                  }
              } else {
                  System.out.println("Ожидался аргумент " + (args.length - 1));
                  System.out.println("id необходим");
              }
              break;

          case ("clear"):

              return new CollectionCommand_Clear();
          case ("save"):

              return  new CollectionCommand_Save();
          case ("execute_script"):

              if (args.length == 2) {
                  try {
                      return new CollectionCommand_Execute_script(args[1]);
                  } catch (Exception e) {
                      System.out.println("Ошибка выполнения : " + e.getMessage());
                  }
              } else {
                  System.out.println("Ожидался 1 аргумент ,но найдено " + (args.length - 1));
                  System.out.println("Необходим путь файла ");
              }
              break;
          case ("exit"):

              return new CollectionCommand_Exit(true);
          case ("add_if_max"):

              return new CollectionCommand_Add_if_Max(Ticket.TicketBuilder());

          case ("remove_greater"):

              return new CollectionCommand_Remove_greater(Ticket.TicketBuilder());
          case ("print_field_descending_comment") :

              return new CollectionCommand_PrintComment();
          case ("print_field_descending_price"):

              return  new CollectionCommand_PrintPrice();
          case "filter_less_than_event":

              if(args.length==2){
                  try {
                      return new CollectionCommand_filter_less_than_event(Long.parseLong(args[1]));
                  }catch (NumberFormatException var5) {
                      System.out.println(args[1] + " Воу воу палехче.Слишком большой price");
                  } catch (Exception var6) {
                      System.out.println("Эксепшон");
                  }
              }else
                  System.out.println("Ожидался 1 аргумент ,но найдено " + (args.length - 1));


          default:

              System.out.println("Неизвестная комманда: " + args[0]);
              System.out.println("Напишите help для помощи");
              break;
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




