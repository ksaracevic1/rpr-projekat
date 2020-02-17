package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAOXML;
import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Exceptions.InvalidSearchTermException;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import ba.unsa.etf.rpr.projekat.JasperReport;
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
import net.sf.jasperreports.engine.JRException;

import java.util.ResourceBundle;


public class MainController extends Controller {

    private DatabaseDAO dao;
    private Account accountInUse;
    private VideoGame clickedGame;

    enum SearchType {
        All,
        Name,
        Genre,
        Developer,
    }

    public BorderPane mainBorder;
    public Menu usernameMenu;
    public TextField searchFieldVG, searchFieldDV;
    public ChoiceBox<SearchType> choiceBoxVG,choiceBoxDV;
    public TilePane tilePaneVG, tilePaneDV;
    public ObservableList<VideoGame> videoGames = FXCollections.observableArrayList();
    public ObservableList<Developer> developers=FXCollections.observableArrayList();
    public final ObservableList<SearchType> searchTypesVG =
            FXCollections.observableArrayList(
                    SearchType.All, SearchType.Name, SearchType.Genre, SearchType.Developer);
    public final ObservableList<SearchType> searchTypesDV =
            FXCollections.observableArrayList(
                    SearchType.All, SearchType.Name);
    public MainController(DatabaseDAO dao, Account accountInUse) {
        this.dao = dao;
        this.accountInUse = accountInUse;
    }

    @FXML
    public void initialize() {
        choiceBoxVG.setItems(searchTypesVG);
        choiceBoxVG.setValue(SearchType.All);
        choiceBoxDV.setItems(searchTypesDV);
        choiceBoxDV.setValue(SearchType.All);
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
        clearUI();
        dao = new DatabaseDAODB();
    }

    public void switchXml(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        clearUI();
        dao = new DatabaseDAOXML();
    }

    public void clearUI(){
        tilePaneVG.getChildren().clear();
        tilePaneDV.getChildren().clear();
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
            SearchType selectedType = choiceBoxVG.getValue();
            String search = searchFieldVG.getText();
            if (search.equals("") && selectedType!= SearchType.All) {
                throw new InvalidSearchTermException("Search exception");
            }
            videoGames.clear();
            tilePaneVG.getChildren().clear();
            if (selectedType.equals(SearchType.All)) {
                videoGames.setAll(dao.getVideoGames());
            } else if (selectedType.equals(SearchType.Name)) {
                videoGames.setAll(dao.getVideoGameByName(search));
            } else if (selectedType.equals(SearchType.Developer)) {
                videoGames.setAll(dao.getVideoGameByDeveloper(search));
            } else if (selectedType.equals(SearchType.Genre)) {
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
            alert.setTitle("Exception");
            alert.setHeaderText(e.getLocalizedMessage());
            alert.setContentText("Search field is empty");
            alert.showAndWait();
        }
    }

    public void searchDV(ActionEvent actionEvent) {
        try {
            SearchType selectedType = choiceBoxDV.getValue();
            String search = searchFieldDV.getText();
            if (search.equals("") && selectedType!= SearchType.All) {
                throw new InvalidSearchTermException("Search exception");
            }
            developers.clear();
            tilePaneDV.getChildren().clear();
            if (selectedType.equals(SearchType.All)) {
                developers.setAll(dao.getDevelopers());
            } else if (selectedType.equals(SearchType.Name)) {
                developers.setAll(dao.getDeveloperByName(search));
            }
            for (Developer dv : developers) {
                Button button = new Button();
                button.setOnMouseClicked(event -> {
                    openDeveloperView(dv);
                });
                new Thread(()->{
                    Image image=new Image(dv.getIconLink());
                    Platform.runLater(()->{
                        ImageView imageView=new ImageView(image);
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        button.setGraphic(imageView);
                        tilePaneDV.getChildren().add(button);
                    });
                }).start();
            }
        } catch (InvalidSearchTermException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setHeaderText(e.getLocalizedMessage());
            alert.setContentText("Search field is empty");
            alert.showAndWait();
        }
    }

    public void openGameView(VideoGame videoGame) {
        if(accountInUse instanceof UserAccount) {
            UIControl.openWindow(getClass(), new GameViewController(videoGame), ResourceBundle.getBundle("Language"), "gameDetails.fxml");
        } else{
            //todo adminview
        }
    }

    public void openDeveloperView(Developer dv) {
        if(accountInUse instanceof  UserAccount) {
            UIControl.openWindow(getClass(), new DeveloperViewController(dv), ResourceBundle.getBundle("Language"), "developerDetails.fxml");
        } else{
            //todo adminview
        }
    }

    public void showReport(ActionEvent actionEvent){
        if(dao instanceof DatabaseDAODB) {
            DatabaseDAODB dbDao=(DatabaseDAODB)dao;
            try {
                new JasperReport().showReport(dbDao.getConn());
            } catch (JRException e1) {
                e1.printStackTrace();
            }
        }
    }
}
