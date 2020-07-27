package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Команда save
 */
public class CollectionCommand_Show implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 15L;

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){

        Iterator<Ticket> iter=tickets.iterator();
        while (iter.hasNext()){
            System.out.println('\n' + iter.next().toString() + '\n');
        }
    }



}
