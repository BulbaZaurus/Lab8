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
    Connection connection;
    Statement statement;
    ArrayList<Integer> IdList = new ArrayList<>();

    public CollectionCommand_Update_id(int id) {
        this.id = id;
    }
    public void Prepare(){
        newTicket=Ticket.TicketBuilder();
    }

    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user) {
        connection = Server.getConnection();
        statement = Server.getStatement();
        try{
            newTicket.setUser(user);
            Ticket ticketToRemove=null;
            for(Ticket ticket:tickets){
                if(ticket.getId()==id){
                    ticketToRemove=ticket;
                }
            }
            if(ticketToRemove!=null){
                if(ticketToRemove.getUser().getName().equals(user.getName())){
                    Ticket ticket=newTicket;
                    ticket.id=id;
                    tickets.add(ticket);
                    DBSavingProtocol.UpdateElementInDatabase(ticket,connection,statement);
                    System.out.println("Элемент с id = "+id+" был обновлен");
                }else {
                    System.out.println("ОПА! Ловушка Письмака");
                    System.out.println("Вы не можете редактировать этот эллемент");
                }

            }else {
                System.out.println("Билет с id = "+id + "не был найде");
            }
        }catch (Exception e){
            System.out.println("ОШИБКА");
            e.printStackTrace();
        }
        /*String sql = "select id from tic where username ='"+user.getName()+"'";
        try {
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next());
            Integer ID=resultSet.getInt("id");
            IdList.add(ID);
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {

            boolean found=false;

            found = tickets.removeIf((i) -> {
                return i.id == id;
            });
            if(found & IdList.contains(id)) {
                Ticket org = newticket;
                org.id = id;
                tickets.add(org);
                System.out.println("Успешно обновлен эллемент = " + id + ".");
            }else{
                System.out.println("Эллемент с данным id не найден или вы не имеете к нему доступа ");
            }
        } catch (Exception e) {
            System.out.println("Ошибка....");
        }

    }
    public void Prepare(){
        newticket=Ticket.TicketBuilder();
    }

         */
    }
}

