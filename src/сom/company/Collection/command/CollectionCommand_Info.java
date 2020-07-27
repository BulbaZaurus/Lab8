package сom.company.Collection.command;


import сom.company.Collection.Ticket;
import сom.company.Collection.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда info
 */
public class CollectionCommand_Info implements  Serializable, CollectionCommand {
    private  static  final  long serialVersionUID = 10L;

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){
        LocalDateTime dateTime = null;
        System.out.println("Тип: TreeSet \n" +
                "Количество элементов: " + tickets.size() );
    }
    }


