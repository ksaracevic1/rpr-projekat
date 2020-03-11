package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAOXML;
import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Interfaces.DataControl;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import ba.unsa.etf.rpr.projekat.JasperReport;
import ba.unsa.etf.rpr.projekat.UIControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class AdminController extends Controller implements DataControl {

    private DatabaseDAO dao;
    private AdminAccount accountInUse;
    private ResourceBundle bundle = ResourceBundle.getBundle("Language");

    public BorderPane mainBorder;
    public Menu usernameMenu;
    public ListView<VideoGame> VGListView;
    public ListView<Developer> DVListView;
    public ListView<UserAccount> userListView;
    public ListView<AdminAccount> adminListView;

    public AdminController(DatabaseDAO dao, AdminAccount accountInUse) {
        this.dao = dao;
        this.accountInUse = accountInUse;
    }

    @FXML
    public void initialize() {
        usernameMenu.setText(accountInUse.getUsername());
        ImageView imageView=new ImageView();
        try {
            imageView.setImage(new Image(new FileInputStream("resources/img/admin.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setFitWidth(15);
        imageView.setFitHeight(15);
        usernameMenu.setGraphic(imageView);
    }

    @Override
    public void switchDb(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        clearUI();
        dao = new DatabaseDAODB();
    }

    @Override
    public void switchXml(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        clearUI();
        dao = new DatabaseDAOXML();
    }

    @Override
    public void clearUI() {

    }

    public void clickAbout(ActionEvent actionEvent) {
        UIControl.openWindow(getClass(), null, bundle, "about.fxml");
    }

    public void clickLogout(ActionEvent actionEvent) {
        Stage stage = (Stage) mainBorder.getScene().getWindow();
        stage.close();
    }

    public void showReport(ActionEvent actionEvent) {
        if (dao instanceof DatabaseDAODB) {
            DatabaseDAODB dbDao = (DatabaseDAODB) dao;
            try {
                new JasperReport().showReport(dbDao.getConn());
            } catch (JRException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void searchVG(ActionEvent actionEvent) {}

    public void searchDV(ActionEvent actionEvent) {}

    public void searchUsers(ActionEvent actionEvent) {}

    public void searchAdmins(ActionEvent actionEvent) {}
}
