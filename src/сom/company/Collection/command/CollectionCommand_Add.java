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
 * Комманда add
 */
public class CollectionCommand_Add implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 1L;
    Ticket element;
    public CollectionCommand_Add(Ticket ticket){element=ticket;}

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        System.out.println("Добавляю элементы в суп");
        try {
            element.setUser(user);
            tickets.add(element);
            DBSavingProtocol.AddNewElementToDatabaseAndReturnId(element, Server.getConnection(),Server.getStatement());
            System.out.println("Элемент успешно добавлен");
        }catch (Exception e){
            System.out.println("Здрасте,а тут экшепшон");
            e.printStackTrace();
        }
    }
}
