package сom.company.Networking;

import сom.company.Collection.*;

import java.sql.*;
import java.util.TreeSet;

/**
 * Класс,который отвечает за чтение и загрузку данных в БД
 */
public class DBSavingProtocol {
    private static final String tableName = "tickets";

    private static void CheckingForExistence(Connection connection){
        try {
            if (!isTableExists(connection)) {
                CreateTable(connection);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static void CreateTable(Connection connection){
        try{
            System.out.println("Создаем новую таблицу,Владыка! :" + tableName); //                                                                             name,coordinatesx,coordinatesy,price,comment,type,eventtype,eventname,refundable,username,creationdate
            connection.createStatement().executeQuery("CREATE TABLE "+tableName+"(id BIGSERIAL NOT NULL PRIMARY KEY,name VARCHAR(100) NOT NULL,coordinatesX FLOAT NOT NULL, coordinatesY FLOAT NOT NULL,price Float NOT NULL,comment VARCHAR NOT NULL,type VARCHAR(20) NOT NULL,eventType VARCHAR(40) NOT NULL,eventName VARCHAR  NOT NULL,eventID BIGSERIAL NOT NULL UNIQUE ,refundable VARCHAR  NOT NULL , userName VARCHAR NOT NULL,creationDate VARCHAR  NOT NULL )");
        }
        catch(SQLException e){e.printStackTrace();}
    }

    private static boolean isTableExists(Connection connection) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
    }

    public static TreeSet<Ticket> LoadCollection(Connection connection, Statement statement) {
        try {
            CheckingForExistence(connection);
            TreeSet<Ticket> tickets = new TreeSet<Ticket>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tickets");
            while (resultSet.next()) {
                Ticket newTicket = new Ticket();
                newTicket.setId(resultSet.getInt("id"));
                System.out.println(resultSet.getInt("id"));
                newTicket.setName(resultSet.getString("name"));
                Coordinates coordinates;
                Float coordx;
                coordx=Float.parseFloat(String.valueOf(resultSet.getFloat("coordinatesx")));
                //newTicket.coordinates.setX(Float.parseFloat(resultSet.getString("coordinatesx")));
                Double coordy;
                coordy=Double.parseDouble(String.valueOf(resultSet.getDouble("coordinatesy")));
                coordinates= new Coordinates(coordx,coordy);
                newTicket.setCoordinates(coordinates);
                //newTicket.coordinates.setY(Double.parseDouble(resultSet.getString("coordinatesy")));
                newTicket.setPrice(Float.parseFloat(resultSet.getString("price")));
                newTicket.setComment(resultSet.getString("comment"));
                TicketType type;
                type=TicketType.valueOf(resultSet.getString("type"));
                newTicket.setType(type);
                Event event= new Event();
                EventType eventType;
                eventType=EventType.valueOf(resultSet.getString("eventtype"));
                event.setEventType(eventType);
                event.setName(resultSet.getString("eventname"));
                event.setId(resultSet.getLong("id"));
                newTicket.setEvent(event);
                newTicket.setCreationDate(Ticket.StringToDate(resultSet.getString("creationdate")));
                User user = new User();
                user.setName(resultSet.getString("userName"));
                newTicket.setUser(user);
                newTicket.setRefundable(Boolean.parseBoolean(resultSet.getString("refundable")));
                tickets.add(newTicket);
            }
            return tickets;
        } catch (SQLException e) {
            System.out.println("Эксепшон,Владыка!");
            e.printStackTrace();

        }
        return  null;
    }

    public static int AddNewElementToDatabaseAndReturnId(Ticket ticket, Connection connection, Statement statement) throws Exception {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tickets ( name,coordinatesX,coordinatesY,price,comment,type,eventType,eventName,refundable,userName,creationDate) values (?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            System.out.println(Client.getUser());
            System.out.println("ТУТ ИМЯ");
            preparedStatement.setString(1,ticket.getName());
            preparedStatement.setFloat(2,ticket.coordinates.getX());
            preparedStatement.setDouble(3,ticket.coordinates.getY());
            preparedStatement.setFloat(4,ticket.getPrice());
            preparedStatement.setString(5,ticket.getComment());
            preparedStatement.setString(6,ticket.getType().toString());
            preparedStatement.setString(7,ticket.getEvent().getEventType().toString());
            preparedStatement.setString(8,ticket.getEvent().getName());
            preparedStatement.setBoolean(9,ticket.getRefundable());
            preparedStatement.setString(10, ticket.getUser().getName());
            preparedStatement.setString(11,ticket.DateToString());

            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            return keys.getInt(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        throw new Exception("Невозможно добавить новый эллемент ");
    }

    public static void UpdateElementInDatabase(Ticket ticket, Connection connection, Statement statement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tickets SET name=?,coordinatesX=?,coordinatesY=?,price = ?, comment=?,type=?,eventType=?,eventName=?,refundable=?,username=?,creationdate=? WHERE id=?");
            preparedStatement.setString(1,ticket.getName());
            preparedStatement.setFloat(2,ticket.coordinates.getX());
            preparedStatement.setDouble(3,ticket.coordinates.getY());
            preparedStatement.setFloat(4,ticket.getPrice());
            preparedStatement.setString(5,ticket.getComment());
            preparedStatement.setString(6,ticket.getType().toString());
            preparedStatement.setString(7,ticket.getEvent().getEventType().toString());
            preparedStatement.setString(8,ticket.getEvent().getName());
            preparedStatement.setBoolean(9,ticket.getRefundable());
            preparedStatement.setString(10, ticket.getUser().getName());
            preparedStatement.setString(11,ticket.DateToString());
            preparedStatement.setInt(12,Integer.parseInt(Long.toString(ticket.getId())));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteElementInDatabase(Ticket ticket, Connection connection, Statement statement){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tickets WHERE id=?");
            preparedStatement.setInt(1,Integer.parseInt(Long.toString(ticket.getId())));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
