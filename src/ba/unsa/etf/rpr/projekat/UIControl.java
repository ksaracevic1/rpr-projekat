package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.Controllers.Controller;
import ba.unsa.etf.rpr.projekat.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class UIControl {
    public static void openWindow(Class cls,Controller controller, ResourceBundle bundle, String fxmlFile){
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(cls.getResource("/fxml/"+fxmlFile));
            loader.setController(controller);
            loader.setResources(bundle);
            root = loader.load();
            stage.setTitle("Video Game Database");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
