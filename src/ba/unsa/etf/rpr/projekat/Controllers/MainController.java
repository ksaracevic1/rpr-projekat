package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAOXML;
import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Exceptions.InvalidSearchTermException;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import ba.unsa.etf.rpr.projekat.UIControl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainController extends Controller {

    private DatabaseDAO dao;
    private Account accountInUse;
    private VideoGame clickedGame;

    enum GameSearchType {
        All,
        Name,
        Genre,
        Developer,
    }

    public BorderPane mainBorder;
    public Menu usernameMenu;
    public TextField searchFieldVG, searchFieldDV;
    public ChoiceBox<GameSearchType> choiceBoxVG;
    public TilePane tilePaneVG, tilePaneDV;
    public ObservableList<Button> buttons = FXCollections.observableArrayList();
    public ObservableList<VideoGame> videoGames = FXCollections.observableArrayList();
    public ObservableList<Developer> developers=FXCollections.observableArrayList();
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
        usernameMenu.setText(accountInUse.getUsername());
        if(accountInUse instanceof UserAccount){
            new Thread(()->{
                UserAccount ua=(UserAccount) accountInUse;
                Image image=new Image(ua.getAvatarLink());
                Platform.runLater(()->{
                    ImageView imageView=new ImageView(image);
                    imageView.setFitWidth(15);
                    imageView.setFitHeight(15);
                    usernameMenu.setGraphic(imageView);
                });
            }).start();
        }

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
        UIControl.openWindow(getClass(), null, null, "about.fxml");
    }

    public void clickLogout(ActionEvent actionEvent) {
        Stage stage = (Stage) mainBorder.getScene().getWindow();
        stage.close();
    }

    public void searchVG(ActionEvent actionEvent) {
        try {
            GameSearchType selectedType = choiceBoxVG.getValue();
            String search = searchFieldVG.getText();
            if (search.equals("") && selectedType!=GameSearchType.All) {
                throw new InvalidSearchTermException("%searchException");
            }
            videoGames.clear();
            if (selectedType.equals(GameSearchType.All)) {
                videoGames.setAll(dao.getVideoGames());
            } else if (selectedType.equals(GameSearchType.Name)) {
                videoGames.setAll(dao.getVideoGameByName(search));
            } else if (selectedType.equals(GameSearchType.Developer)) {
                videoGames.setAll(dao.getVideoGameByDeveloper(search));
            } else if (selectedType.equals(GameSearchType.Genre)) {
                videoGames.setAll(dao.getVideoGameByGenre(search));
            }
            for (VideoGame vg : videoGames) {
                Button button = new Button();
                button.setOnMouseClicked(event -> {
                    openGameView(vg);
                });
                new Thread(()->{
                    Image image=new Image(vg.getImageLink());
                    Platform.runLater(()->{
                        ImageView imageView=new ImageView(image);
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        button.setGraphic(imageView);
                        tilePaneVG.getChildren().add(button);
                    });
                }).start();
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

    public void openGameView(VideoGame videoGame) {

    }
}
