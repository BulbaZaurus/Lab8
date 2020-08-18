package сom.company.Collection.command;

import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.GUI.MainForm;
import сom.company.Networking.Servermachine.Server;

import javax.swing.*;
import java.io.Serializable;
import java.util.*;

/**
 * Команда save
 */
public class CollectionCommand_Show implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 15L;


    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user) {
        MainForm.setTickets(Controller.getTickets());
        Iterator<Ticket> iter = tickets.iterator();
        while (iter.hasNext()){

            System.out.println(iter.next().toString());
            }
    }
}











