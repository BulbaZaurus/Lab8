package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда help
 */
public class CollectionCommand_Help implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 9L;
    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        for(String line :help){
            System.out.println(line);
        }

    }
    static String[] help= new String[]{
            "help : вывести справку по доступным командам",
                    "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
                    "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
                    "add {element} : добавить новый элемент в коллекцию",
                    "update_id {ID} : обновить значение элемента коллекции, id которого равен заданному",
                    "remove_by_id id : удалить элемент из коллекции по его id",
                    "clear : очистить коллекцию",
                    "save : сохранить коллекцию в файл",
                    "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
                    "exit :завершить программу (с сохранением в файл) ",
                    "add_if_max  : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего  price элемента этой коллекции",
                    "remove_greater  : удалить из коллекции все элементы, превышающие заданный price",
                    "filter_less_than_event eventID: вывести элементы, значение поля event которых меньше заданного",
                    "print_field_descending_comment : вывести значения поля comment всех элементов в порядке убывания",
                    "print_field_descending_price : вывести значения поля price всех элементов в порядке убывания"
    };
}
