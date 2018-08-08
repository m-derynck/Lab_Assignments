package Ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultsDAO implements DAO<Result> {

    private GameDAO gameDAO = new GameDAO();
    private PlayerDAO playerDAO = new PlayerDAO();

    private Result createResult(ResultSet rs) {
        Result result = new Result();
        try {
            result.setGame(gameDAO.get(rs.getInt("game_id")));
            result.setPlayer(playerDAO.get(rs.getInt("player_id")));
            result.setDatePlayed(rs.getDate("playing_date"));
            result.setScore(rs.getInt("score"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Result get(int id) {
        String sqlStatement = String.format("Select * from PlayerAndGame where player_game_id = %d", id);
        Result result = new Result();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            if (rs.next()) {
                result = createResult(rs);
            }
            con.close();
            statement.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<Result> getPlayerResults(int playerId) {
        String sqlStatement = String.format("Select * from PlayerAndGame where player_id = %d order by player_game_id", playerId);
        List<Result> resultList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                Result result = createResult(rs);
                resultList.add(result);
            }
            con.close();
            statement.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public List<Result> getGameResults(int gameId) {
        String sqlStatement = String.format("Select * from PlayerAndGame where game_id = %d order by player_game_id", gameId);
        List<Result> resultList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                Result result = createResult(rs);
                resultList.add(result);
            }
            con.close();
            statement.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    @Override
    public List<Result> getAll() {
        String sqlStatement = "Select * from PlayerAndGame order by player_game_id";
        List<Result> resultList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                Result result = createResult(rs);
                resultList.add(result);
            }
            con.close();
            statement.close();
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            //Display exception in a window here.
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    @Override
    public void save(Result result) {
        String sqlStatement = String.format("Insert into PlayerAndGame (game_id,player_id,playing_date,score) values (%d,%d,'%s',%d);",
                result.getGame().getGameId(),
                result.getPlayer().getPlayerID(),
                new SimpleDateFormat("yyyy-MM-dd").format(result.getDatePlayed()),
                result.getScore()
        );
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DATABASE_URL);
            Statement statement = con.createStatement();
            statement.execute(sqlStatement);
            con.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    //If we need to delete add the functionality here.
    @Override
    public void delete(Result result) {

    }

    //Don't need to update results.
    @Override
    public void update(Result result, String[] params) {

    }
}
