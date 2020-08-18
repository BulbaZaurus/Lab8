package сom.company.Collection.command;


import сom.company.Networking.DBSavingProtocol;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.Networking.Servermachine.Server;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Команда update_id
 */
public class CollectionCommand_Update_id implements CollectionCommand, Serializable {
    private static final long serialVersionUID = 16L;
    public int id;
    Ticket newTicket;
    public Ticket ticket;
    Connection connection;
    Statement statement;
    public CollectionCommand_Update_id(Ticket ticket){
        this.ticket=ticket;

    }


    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user) {
        connection = Server.getConnection();
        statement = Server.getStatement();

        try {
            System.out.println(user.toString());
            ticket.setUser(user);
            DBSavingProtocol.UpdateElementInDatabase(ticket,connection,statement);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Неудача");
        }
    }
}

