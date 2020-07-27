package сom.company.Collection.command;

import сom.company.Networking.DBSavingProtocol;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.Networking.Servermachine.Server;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 *  Команда Remove_greater
 */
public class CollectionCommand_Remove_greater implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 13L;
    Ticket element;
    public CollectionCommand_Remove_greater(Ticket ticket){
        element=ticket;
    }

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        try {
            if(tickets.size()!=0){
                Ticket ticketToRemove=null;
                for(Ticket ticket:tickets){
                    if(ticket.getPrice()>element.getPrice()){
                        ticketToRemove=ticket;
                    }
                }
                if(ticketToRemove!=null){
                    if(ticketToRemove.getUser().getName().equals(user.getName())){
                        tickets.remove(ticketToRemove);
                        DBSavingProtocol.DeleteElementInDatabase(ticketToRemove, Server.getConnection(),Server.getStatement());
                        System.out.println("Билет с большей ценной был удален ");
                    }else {
                        System.out.println("Это не ваш билет,Владыка!");
                    }
                }else {
                    System.out.println("Билет с данной ценой не был найден");
                }
                System.out.println("Типо успех");
            }else {
                System.out.println("Пусто у нас.Нельзя удалить то,что не существует");
            }
        } catch (Exception e) {
            System.out.println("ЭКСЕПШОН");
            e.printStackTrace();
        }
    }
}
