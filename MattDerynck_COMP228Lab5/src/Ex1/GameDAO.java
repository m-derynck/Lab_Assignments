package Ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements DAO<Game> {

    //Creates a game object from the incoming result set. Used to construct tables/views.
    private Game createGame(ResultSet rs){
        Game game = new Game();
        try{
            game.setGameId(rs.getInt("game_id"));
            game.setGameTitle(rs.getString("game_title"));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return game;
    }

    @Override
    public Game get(int id) {
        String sqlStatement = String.format("Select * from Game where game_id = %d", id);
        Game game = new Game();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            if(rs.next()) {
                game = createGame(rs);
            }
            con.close();
            statement.close();
            rs.close();

        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return game;
    }

    @Override
    public List<Game> getAll() {
        String sqlStatement = "Select * from Game order by game_title";
        List<Game> gameList = new ArrayList<>();
        try{
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()){
                Game game = createGame(rs);
                gameList.add(game);
            }
            con.close();
            statement.close();
            rs.close();
        }
        catch(SQLException | ClassNotFoundException e ){
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return gameList;
    }

    @Override
    public void save(Game game) {
        String sqlStatement = String.format("Insert into Game (game_title) values ('%s');",
                game.getGameTitle()
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
    public void delete(Game game) {

    }

    @Override
    public void update(Game game, String[] params) {

    }
}
