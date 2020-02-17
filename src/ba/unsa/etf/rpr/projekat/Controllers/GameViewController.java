package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameViewController extends Controller {

    public Label gameName;
    public Label gameDeveloper;
    public Label gameGenre;
    public Label gameReleaseDate;
    public Label gameDescription;
    public ImageView gameImage;
    private VideoGame selectedGame;

    public GameViewController(VideoGame videoGame){
        this.selectedGame=videoGame;
    }

    @FXML
    public void initialize(){
        gameName.setText(selectedGame.getName());
        gameGenre.setText(selectedGame.getGenre());
        gameDeveloper.setText(selectedGame.getDeveloper().getName());
        gameReleaseDate.setText(selectedGame.getReleaseDate().toString());
        gameDescription.setText(selectedGame.getDescription());
        new Thread(()->{
            Image image=new Image(selectedGame.getImageLink());
            Platform.runLater(()->{
                gameImage.setImage(image);
                gameImage.setFitHeight(250);
                gameImage.setFitWidth(250);
            });
        }).start();
    }
}
