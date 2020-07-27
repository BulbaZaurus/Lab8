package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда exit
 */
public class CollectionCommand_Exit implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 6L;
    boolean force;
    public CollectionCommand_Exit(boolean force) {
        this.force = force;
    }
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        if (!this.force) {
            System.out.println("Шо упало то пропало.");
        }

        if (this.force ) {
            System.out.println("Выключаюсь....");
            System.exit(0);
        }
    }

}
