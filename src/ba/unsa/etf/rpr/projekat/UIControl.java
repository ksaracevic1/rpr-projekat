package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.Controllers.Controller;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class UIControl {
    public static void openWindow(Class cls,Controller controller, ResourceBundle bundle, String fxmlFile){
        openWindow(cls,controller,bundle,fxmlFile,bundle.getString("vgDatabase"));
    }

    public static void openWindow(Class cls,Controller controller, ResourceBundle bundle, String fxmlFile, String title){
        openWindow(cls,controller,bundle,fxmlFile,title,true);
    }

    public static void openWindow(Class cls,Controller controller, ResourceBundle bundle, String fxmlFile, Boolean resizable){
        openWindow(cls,controller,bundle,fxmlFile,bundle.getString("vgDatabase"),resizable);
    }

    public static void openWindow(Class cls,Controller controller, ResourceBundle bundle, String fxmlFile, String title, Boolean resizeable){
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(cls.getResource("/fxml/"+fxmlFile));
            if(controller!=null) {
                loader.setController(controller);
            }
            loader.setResources(bundle);
            root = loader.load();
            stage.setTitle(title);
            stage.getIcons().add(new Image(new FileInputStream("resources/img/controller.png")));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(resizeable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public static void loadImage(ImageView imageView,String imageLink,double height, double width){
        new Thread(()->{
            Image image=new Image(imageLink);
            Platform.runLater(()->{
                imageView.setImage(image);
                imageView.setFitHeight(height);
                imageView.setFitWidth(width);
            });
        }).start();
    }
}
