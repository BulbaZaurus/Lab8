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


    public static boolean TryLogIn(User user, Connection connection){
        EnsureTheTableExists(connection);

        try
        {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE login = ?");
            preparedStatement.setString(1, user.getName());
            System.out.println(Encrypt.EncryptMD(user.getPassword()));
            ResultSet resultSet = preparedStatement.executeQuery();

            
            boolean isResultSetEmpty = !resultSet.next();
            if(isResultSetEmpty){
                return false;
            }

            if(user.getName().equals(resultSet.getString("login")) && Encrypt.EncryptMD(user.getPassword()).equals(resultSet.getString("password"))) {
                return true;
            }
            System.out.println("NOT PASS");
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return false;

    }


    public static boolean TrySignIn(User user,Connection connection) {
        EnsureTheTableExists(connection);

        try{

           PreparedStatement tryToFindUserWithTheSameId = connection.prepareStatement("SELECT login FROM users WHERE login=?");
            tryToFindUserWithTheSameId.setString(1,user.getName());
            ResultSet rs = tryToFindUserWithTheSameId.executeQuery();


            if(rs.next()){

                tryToFindUserWithTheSameId.close();
                rs.close();
                return false;
            }
            tryToFindUserWithTheSameId.close();

            rs.close();




            String sql = "insert into users (login, password) values (?,?)";
            PreparedStatement insertNewUserStatement = connection.prepareStatement(sql);
            insertNewUserStatement.setString(1, user.getName());
            insertNewUserStatement.setString(2, Encrypt.EncryptMD(user.getPassword()));
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
