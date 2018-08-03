package Ex1;

import java.util.Date;

public class Result {

    private int resultId;
    private Game game;
    private Player player;
    private Date datePlayed;
    private int score;

    public Result() {}

    public Result(Game game, Player player, Date datePlayed, int score) {
        this.game = game;
        this.player = player;
        this.datePlayed = datePlayed;
        this.score = score;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
