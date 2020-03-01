package ba.unsa.etf.rpr.projekat.DTO;

public class GameReview implements Comparable {
    private VideoGame videoGame;
    private UserAccount account;
    private Integer score;
    private String comment;

    public GameReview(VideoGame videoGame, UserAccount account, Integer score, String comment) {
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

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
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
