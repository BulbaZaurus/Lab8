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

            DBSavingProtocol.DeleteElementInDatabase(element, Server.getConnection(),Server.getStatement());
        } catch (Exception e) {
            System.out.println("ЭКСЕПШОН");
            e.printStackTrace();
        }
    }
}
