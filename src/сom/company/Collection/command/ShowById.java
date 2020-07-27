package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Мы сами не знаем зачем этот метод,но он нужен был для 6 лабы
 */
public class ShowById implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 1516767656987L;
    public int id;
    public ShowById (int id){
        this.id=id;
    }
    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        for(Ticket ticket:tickets){
            if(ticket.id==id){
                System.out.println("---------");
                System.out.println(Ticket.TicketToString(ticket,true));
            }
        }

    }
}
