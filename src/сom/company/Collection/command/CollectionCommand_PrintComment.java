package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.*;
/**
 * Комманда print_comment
 */
public class CollectionCommand_PrintComment implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 11L;
    String[] args;
    public CollectionCommand_PrintComment() {

    }

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        if(tickets.size()!=0){
            NavigableSet<Ticket> treereverse=tickets.descendingSet();
            Iterator<Ticket> iterator=treereverse.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next().print_Comment());
            }

        }else
            System.out.println("Коллекция пустая на данный момент.Добавь эллемент и попробуй снова");
    }
}
