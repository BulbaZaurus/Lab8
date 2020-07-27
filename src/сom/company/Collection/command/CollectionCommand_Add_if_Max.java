package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда add_if_max
 */
public class CollectionCommand_Add_if_Max implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 2L;
    Ticket element;
    public CollectionCommand_Add_if_Max(Ticket ticket){
        element=ticket;
    }

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        try {
            if(tickets.size()!=0){
                if(element.compareTo(tickets.last())>0){
                    new CollectionCommand_Add(element).Execute(tickets, in, savePath, isScannerFromSystem, executed_scripts,user);

                }else{
                    System.out.println(" Сорянба.Price должен быть больше чем "+ tickets.last().getPrice());
                }
            }else
                System.out.println("А шо  ты хочешь,Мойша? Коллекция и так пустая.С чем сравнивать будешь?");
        }catch (Exception e){
            System.out.println("Опча.Ошибка при add_if_max");
        }

    }
}
