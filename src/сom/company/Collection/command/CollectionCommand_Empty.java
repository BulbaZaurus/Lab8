package сom.company.Collection.command;

import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.GUI.MainForm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class CollectionCommand_Empty implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 1421454535355L;

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user) {
    }
}
