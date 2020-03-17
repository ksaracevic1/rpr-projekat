package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.ObservableList;

public class GameFormController extends Controller {

    private VideoGame videoGame;
    private ObservableList<VideoGame> videoGames;
    private DatabaseDAO dao;

    public GameFormController(VideoGame videoGame, ObservableList<VideoGame> videoGames, DatabaseDAO dao) {
        this.videoGame = videoGame;
        this.videoGames = videoGames;
        this.dao = dao;
    }
}
