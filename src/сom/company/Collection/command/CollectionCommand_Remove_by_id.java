package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.Networking.DBSavingProtocol;
import сom.company.Networking.Servermachine.Server;


import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда Remove_by_id
 */
public class CollectionCommand_Remove_by_id implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 12L;
    long id;
    public CollectionCommand_Remove_by_id(long id){this.id=id;}
    Ticket ticketToRemove=null;

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        boolean found=false;
        /*try {
            found = tickets.removeIf((i) -> {
                return i.id == id;
            });
            if(found) {
                System.out.println("Успешно удален эллемент с id  = " + id + ".");
            }else{
                System.out.println("Не обнаружен билет с данным id");
            }
        } catch (Exception e) {
            System.out.println("ЭКСЕПШОН");
        }

         */
        try{
            for(Ticket ticket:tickets){
                if(ticket.getId()==id){
                    ticketToRemove=ticket;
                }
            }
            if(ticketToRemove!=null){
                if(ticketToRemove.getUser().getName().equals(user.getName())){
                    tickets.remove(ticketToRemove);
                    DBSavingProtocol.DeleteElementInDatabase(ticketToRemove, Server.getConnection(),Server.getStatement());
                    System.out.println("Билеты с id = " +id+" были удалены ");
                }else {
                    System.out.println("Владыка,эти билеты не пренадлежат вам");
                }
            }else{
                System.out.println("Не найден билет с таким id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
