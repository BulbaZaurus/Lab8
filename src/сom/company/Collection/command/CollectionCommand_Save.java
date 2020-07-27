package сom.company.Collection.command;

import сom.company.Collection.Ticket;
import сom.company.Collection.User;


import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Команда save
 */
public class CollectionCommand_Save implements  Serializable, CollectionCommand {

    private static final long serialVersionUID = 14L;
    private final String HOST ="jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME="postgres";
    private final String PASSWORD="root";
    private  final String sql="INSERT INTO  tic (name,coordinatesx,coordinatesy,price,comment,type,eventtype,eventname,refundable,username,creationdate) values (?,?,?,?,?,?,?,?,?,?,?)";



    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){

        try {
            Connection connection= DriverManager.getConnection(HOST,USERNAME,PASSWORD);
            Statement statement=connection.createStatement();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            {

                statement.executeUpdate("TRUNCATE tic");
                for (Ticket ticket:tickets){
                    preparedStatement.setString(1,ticket.getName());
                    preparedStatement.setFloat(2,ticket.coordinates.getX());
                    preparedStatement.setDouble(3,ticket.coordinates.getY());
                    preparedStatement.setFloat(4,ticket.getPrice());
                    preparedStatement.setString(5,ticket.getComment());
                    preparedStatement.setString(6,ticket.getType().toString());
                    preparedStatement.setString(7,ticket.getEvent().getEventType().toString());
                    preparedStatement.setString(8,ticket.getEvent().getName());
                    preparedStatement.setBoolean(9,ticket.getRefundable());
                    preparedStatement.setString(10, user.getName());
                    preparedStatement.setString(11,ticket.DateToString());
                    int rows =preparedStatement.executeUpdate();
                    System.out.println(rows);
                }
            }




                //int row= statement.executeUpdate("insert into tickets(name,coordinatesX,coordinatesY,price,comment,type,eventType,eventname,refundable) values ("+name+","+coordx+","+coordy+","+price+","+comment+","+ticket.getType()+","+ticket.getEvent().getEventType()+","+eventname+","+ticket.getRefundable()+")");

        } catch (SQLException e){
            e.printStackTrace();

        }




        ///подумай че сделать как запихнуть в байтовый поток
    }
}
