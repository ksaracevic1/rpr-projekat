package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.GameReview;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import ba.unsa.etf.rpr.projekat.UIControl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;


import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

public class GameViewController extends Controller {

    public Label gameName;
    public Label gameDeveloper;
    public Label gameGenre;
    public Label gameReleaseDate;
    public Label gameDescription;
    public ImageView gameImage;
    public ListView<GameReview> reviewListView;
    private VideoGame selectedGame;
    private DatabaseDAO dao;
    private ObservableList<GameReview> gameReviews = FXCollections.observableArrayList();
    private ResourceBundle bundle=ResourceBundle.getBundle("Language");

    public GameViewController(VideoGame videoGame, DatabaseDAO dao) {
        this.selectedGame = videoGame;
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        gameName.setText(selectedGame.getName());
        gameGenre.setText(selectedGame.getGenre());
        gameDeveloper.setText(selectedGame.getDeveloper().getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        gameReleaseDate.setText(selectedGame.getReleaseDate().format(formatter));
        gameDescription.setText(selectedGame.getDescription());
        UIControl.loadImage(gameImage, selectedGame.getImageLink(), 250, 250);
        Set<GameReview> gameReviewSet = dao.getReviewsByGameId(selectedGame.getId());
        gameReviews.addAll(gameReviewSet);
        reviewListView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView=new ImageView();
            @Override
            public void updateItem(GameReview item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    UIControl.loadImage(imageView,item.getAccount().getAvatarLink(),50,50);
                    setGraphic(imageView);
                    setText(bundle.getString("userColon")+" "+item.getAccount().getUsername()+" "
                            +bundle.getString("scoreColon")+" "+item.getScore()+"/10\n"
                            +bundle.getString("commentColon")+" "+item.getComment());
                    setFont(Font.font(14));
                }
            }
        });
        reviewListView.setItems(gameReviews);
    }

}
