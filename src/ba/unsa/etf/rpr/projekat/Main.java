package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.Controllers.LoginController;
import ba.unsa.etf.rpr.projekat.Controllers.MainController;
import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    private DatabaseDAO dao;

    @Override
    public void start(Stage primaryStage) throws Exception {
        dao=new DatabaseDAODB();
        UIControl.openWindow(getClass(), new LoginController(dao), ResourceBundle.getBundle("Language"), "login.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
