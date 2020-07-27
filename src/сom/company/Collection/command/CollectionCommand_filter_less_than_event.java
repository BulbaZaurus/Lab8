package сom.company.Collection.command;

import сom.company.Collection.Event;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.*;
/**
 * Комманда filter_less_than_event
 */
public class CollectionCommand_filter_less_than_event implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 7L;
    Long args;
    public CollectionCommand_filter_less_than_event(Long args) {
        this.args = args;
    }
    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
            if(tickets.size()!=0){
                NavigableSet<Ticket> finder=tickets;
                Iterator<Ticket> iterator=finder.iterator();
                Ticket t=null;
                Event e=null;
                while (iterator.hasNext()){
                    t=iterator.next();
                    if(t.getEvent().getId()==Long.parseLong(String.valueOf(args))){

                        break;
                    }
                    else {
                        t=null;
                    }

                }
                if(t!=null){
                    e=tickets.lower(t).getEvent();
                }else{
                    System.out.println("event с меньшим id не был найден");
                    return;
                }
                System.out.println(e.toString());

            }else
                System.out.println("Коллекция пустая на данный момент.Добавь эллемент и попробуй снова");
    }
}
