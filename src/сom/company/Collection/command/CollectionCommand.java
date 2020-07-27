package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Интрфейс для всех классов команд
 */
public interface CollectionCommand {
    void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                 String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user);
}
