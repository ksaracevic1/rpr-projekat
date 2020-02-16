package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAOXML;
import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Exceptions.InvalidSearchTermException;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;


public class MainController extends Controller{

    private DatabaseDAO dao;
    private Account accountInUse;
    private VideoGame clickedGame;

    enum GameSearchType {
        All,
        Name,
        Genre,
        Developer,
    }


    public TextField searchFieldVG, searchFieldDV;
    public ChoiceBox<GameSearchType> choiceBoxVG;
    public TilePane tilePaneVG, tilePaneDV;
    public ObservableList<Button> buttons = FXCollections.observableArrayList();
    public ObservableList<VideoGame> videoGames = FXCollections.observableArrayList();
    public final ObservableList<GameSearchType> searchTypes =
            FXCollections.observableArrayList(
                    GameSearchType.All, GameSearchType.Name, GameSearchType.Genre, GameSearchType.Developer);

    public MainController(DatabaseDAO dao, Account accountInUse) {
        this.dao = dao;
        this.accountInUse = accountInUse;
    }

    @FXML
    public void initialize() {
        choiceBoxVG.setItems(searchTypes);
        choiceBoxVG.setValue(GameSearchType.All);
    }


    public void switchDb(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        dao = new DatabaseDAODB();
    }

    public void switchXml(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        dao = new DatabaseDAOXML();
    }

    public void clickAbout(ActionEvent actionEvent) {

    }

    public void clickLogout(ActionEvent actionEvent) {

    }

    public void searchVG(ActionEvent actionEvent) {
        try {
            String search = searchFieldVG.getText();
            if (search.equals("")) {
                throw new InvalidSearchTermException("%searchException");
            }
            videoGames.clear();
            GameSearchType selectedType = choiceBoxVG.getValue();
            if (selectedType.equals(GameSearchType.All)) {
                videoGames.setAll(dao.getVideoGames());
            } else if (selectedType.equals(GameSearchType.Name)) {

            } else if (selectedType.equals(GameSearchType.Developer)) {

            } else if (selectedType.equals(GameSearchType.Genre)) {

            }
            for (VideoGame vg : videoGames) {
                Button button = new Button();
                button.setOnMouseClicked(event -> {
                    openGameView(vg);
                });
            }
        } catch (InvalidSearchTermException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("%exception");
            alert.setHeaderText(e.getLocalizedMessage());
            alert.setContentText("%emptySearch");
            alert.showAndWait();
        }
    }

    public void searchDV(ActionEvent actionEvent) {

    }

    public void openGameView(VideoGame videoGame){

    }
}
