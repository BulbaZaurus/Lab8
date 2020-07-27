package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда clear
 */
public class CollectionCommand_Clear implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 4L;
    public CollectionCommand_Clear() {
    }
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user) {
        tickets.clear();
        System.out.println("Плакали ваши денюжки.Билетов больше нет");
    }
}
