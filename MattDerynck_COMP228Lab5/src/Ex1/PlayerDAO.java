package Ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO implements DAO<Player> {

    //Creates a player object from the incoming result set. Used to construct tables/views.
    private Player createPlayer(ResultSet rs) {
        Player player = new Player();
        try {
            player.setPlayerID(rs.getInt("player_id"));
            player.setFirstName(rs.getString("first_name"));
            player.setLastName(rs.getString("last_name"));
            player.setAddress(rs.getString("address"));
            player.setPostalCode(rs.getString("postal_code"));
            player.setProvince(rs.getString("province"));
            player.setPhoneNumber(rs.getString("phone_number"));
        } catch (SQLException e) {
            //Catch and display any exceptions here.
            System.out.println(e.getMessage());
        }
        return player;
    }

    @Override
    public Player get(int id) {
        String sqlStatement = String.format("Select * from Player where player_id = %d", id);
        Player player = new Player();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            if(rs.next()) {
                player = createPlayer(rs);
            }
            con.close();
            statement.close();
            rs.close();

        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return player;
    }

    @Override
    public List<Player> getAll() {
        String sqlStatement = "Select * from Player order by last_name";
        List<Player> playerList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                Player player = createPlayer(rs);
                playerList.add(player);
            }
            con.close();
            statement.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return playerList;
    }

    @Override
    public void save(Player player) {
        String sqlStatement = String.format("Insert into Player (first_name,last_name,address,postal_code,province,phone_number) values ('%s','%s','%s','%s','%s','%s');",
                player.getFirstName(),
                player.getLastName(),
                player.getAddress(),
                player.getPostalCode(),
                player.getProvince(),
                player.getPhoneNumber()
                );
        try{
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            statement.execute(sqlStatement);
            con.close();
            statement.close();
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    //If we need to delete add the functionality here.
    @Override
    public void delete(Player player) {
        String sqlStatement = String.format("Delete from Player where player_id = %d", player.getPlayerID());
        String cascadeStatement = String.format("Delete from PlayerAndGame where player_id = %d", player.getPlayerID());
        try{
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            statement.execute(cascadeStatement);
            statement.execute(sqlStatement);
            con.close();
            statement.close();
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Player player, String[] params) {

    }
}
