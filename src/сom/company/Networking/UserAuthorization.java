package сom.company.Networking;
import сom.company.Collection.User;
import сom.company.Networking.Cryptography.Encrypt;

import java.io.Serializable;
import java.sql.*;


/**
 * Класс,который отвечает за авторизацию пользователей
 */
public class UserAuthorization implements Serializable {
    private static final String tableName = "users";

    private static void EnsureTheTableExists(Connection connection){
        try {
            if (isUserTableExists(connection)==false) {
                CreateUserTable(connection);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void CreateUserTable(Connection connection){
        try{
            System.out.println("Создаю новую таблицу :" + tableName);
            connection.createStatement().executeQuery("CREATE TABLE "+tableName+" (login varchar, password varchar)");
        }
        catch(SQLException e){e.printStackTrace();}
    }

    private static boolean isUserTableExists(Connection connection) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
    }

    //returns true if logged in successfully. false otherwise
    public static boolean TryLogIn(User user, Connection connection){ // операция залогинивания
        EnsureTheTableExists(connection);

        try
        {
            //find a user with our login
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE login = ?");
            preparedStatement.setString(1, user.getName());
            System.out.println(Encrypt.EncryptMD(user.getPassword()));
            //if the found user login and password match -> return true
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isResultSetEmpty = !resultSet.next();
            if(isResultSetEmpty){
                return false;
            }

            if(user.getName().equals(resultSet.getString("login")) && Encrypt.EncryptMD(user.getPassword()).equals(resultSet.getString("password"))) {
                return true;
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return false;

    }

    //returns true if signed in successfully. false otherwise
    public static boolean TrySignIn(User user,Connection connection) {
        EnsureTheTableExists(connection);

        try{
            //try to find user with the same id
           PreparedStatement tryToFindUserWithTheSameId = connection.prepareStatement("SELECT login FROM users WHERE login=?");
            tryToFindUserWithTheSameId.setString(1,user.getName());
            ResultSet rs = tryToFindUserWithTheSameId.executeQuery();


            if(rs.next()){
                //found user with the same login
                tryToFindUserWithTheSameId.close();
                rs.close();
                return false;
            }
            tryToFindUserWithTheSameId.close();

            rs.close();




            //insert new user
            String sql = "insert into users (login, password) values (?,?)";
            PreparedStatement insertNewUserStatement = connection.prepareStatement(sql);
            insertNewUserStatement.setString(1, user.getName());
            insertNewUserStatement.setString(2, user.getPassword());
            int rows =insertNewUserStatement.executeUpdate();
            System.out.println(rows);
            insertNewUserStatement.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
