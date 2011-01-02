package connectivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author UDP
 */
public class QueryManager {

    private final Dbmanager dbmanager;

    /**
     * Sla het Dbmanager object op voor intern (private) gebruik
     * @param dbmanager
     */
    public QueryManager(Dbmanager dbmanager) {
        this.dbmanager = dbmanager;
    }

    /**
     * Haal het wachtwoord uit de database waar de naam "AccountName" is, indien er geen resultaat is dan
     * @param AccountName
     * @return
     */
    public User getUser(String username){
        User user = new User();
        try{
            String sql = "SELECT * FROM `users` WHERE `gebruikersnaam` = '"+username+"';";
            ResultSet result = dbmanager.doQuery(sql);
            if (result.next()) {
                user = new User(result.getInt("id"),
                        result.getString("gebruikersnaam"),
                        result.getString("wachtwoord"));
            }
        }catch (SQLException e) {
            System.out.println(Dbmanager.SQL_EXCEPTION + e.getMessage());
        }
        return user;
    }

    public User getUser(int id){
        User user = new User();
        try{
            String sql = "SELECT * FROM `users` WHERE `id` = '"+id+"';";
            ResultSet result = dbmanager.doQuery(sql);
            if (result.next()) {
                user = new User(result.getInt("id"),
                        result.getString("gebruikersnaam"),
                        result.getString("wachtwoord"));
            }
        }catch (SQLException e) {
            System.out.println(Dbmanager.SQL_EXCEPTION + e.getMessage());
        }
        return user;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        try{
            String sql = "SELECT * FROM `users`;";
            ResultSet result = dbmanager.doQuery(sql);
            if (result.next()) {
                User medewerker = new User(result.getInt("id"),
                        result.getString("gebruikersnaam"),
                        result.getString("wachtwoord"));
                users.add(medewerker);
            }
        }catch (SQLException e) {
            System.out.println(Dbmanager.SQL_EXCEPTION + e.getMessage());
        }
        return users;
    }

    public void addUser(User employee){
        String SQL = "INSERT INTO `users` (gebruikersnaam, wachtwoord)"
                + "VALUES('" + employee.getGebruikersnaam() + "', '" + employee.getWachtwoord() + "')";
        dbmanager.insertQuery(SQL);
    }

    public void changeUser() {

    }

    public void deleteUser(int userId) {
        String SQL = "DELETE * FROM `users` WHERE `id` = '" + userId + "';";
        dbmanager.insertQuery(SQL);
    }
}
