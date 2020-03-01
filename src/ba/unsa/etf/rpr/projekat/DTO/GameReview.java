package ba.unsa.etf.rpr.projekat.DTO;

public class GameReview implements Comparable {
    private VideoGame videoGame;
    private Account account;
    private Integer score;
    private String comment;

    public GameReview(VideoGame videoGame, Account account, Integer score, String comment) {
        this.videoGame = videoGame;
        this.account = account;
        this.score = score;
        this.comment = comment;
    }

    public GameReview(){
        super();
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame videoGame) {
        this.videoGame = videoGame;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int compareTo(Object o) {
        GameReview gameReview=(GameReview) o;
        return this.getScore().compareTo(gameReview.getScore());
    }
}
