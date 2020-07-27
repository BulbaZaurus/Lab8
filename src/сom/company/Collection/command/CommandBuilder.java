package сom.company.Collection.command;

import сom.company.Collection.Ticket;

/**
 * Класс,который реализует различные команды
 */
public class CommandBuilder {


    public static CollectionCommand Build(String input) {
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
}

