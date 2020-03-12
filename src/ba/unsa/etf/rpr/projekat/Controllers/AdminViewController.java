package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAOXML;
import ba.unsa.etf.rpr.projekat.DTO.*;
import ba.unsa.etf.rpr.projekat.Interfaces.DataControl;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import ba.unsa.etf.rpr.projekat.JasperReport;
import ba.unsa.etf.rpr.projekat.UIControl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class AdminViewController extends Controller implements DataControl {

    private DatabaseDAO dao;
    private AdminAccount accountInUse;
    private ResourceBundle bundle = ResourceBundle.getBundle("Language");

    public BorderPane mainBorder;
    public Menu usernameMenu;
    public ListView<VideoGame> VGListView;
    public ListView<Developer> DVListView;
    public ListView<UserAccount> userListView;
    public ListView<AdminAccount> adminListView;
    private ObservableList<VideoGame> videoGames;
    private ObservableList<Developer> developers;
    private ObservableList<AdminAccount> adminAccounts;
    private ObservableList<UserAccount> userAccounts;

    public AdminViewController(DatabaseDAO dao, AdminAccount accountInUse) {
        this.dao = dao;
        this.accountInUse = accountInUse;
        videoGames=dao.getVideoGames();
        developers=dao.getDevelopers();
        adminAccounts=dao.getAdmins();
        userAccounts=dao.getUsers();
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
        configureLists();
        VGListView.setItems(videoGames);
        DVListView.setItems(developers);
        adminListView.setItems(adminAccounts);
        userListView.setItems(userAccounts);
    }

    private void getAllData(){
        clearUI();
        videoGames.setAll(dao.getVideoGames());
        developers.setAll(dao.getDevelopers());
        adminAccounts.setAll(dao.getAdmins());
        userAccounts.setAll(dao.getUsers());
    }

    @Override
    public void switchDb(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        clearUI();
        dao = new DatabaseDAODB();
        getAllData();
    }

    @Override
    public void switchXml(ActionEvent actionEvent) {
        if (dao != null) dao.close();
        clearUI();
        dao = new DatabaseDAOXML();
        getAllData();
    }

    @Override
    public void clearUI() {
        videoGames.clear();
        developers.clear();
        adminAccounts.clear();
        userAccounts.clear();
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

    public void addVideoGame(ActionEvent actionEvent){

    }

    public void updateVideoGame(ActionEvent actionEvent){

    }

    public void deleteVideoGame(ActionEvent actionEvent){

    }

    public void addDeveloper(ActionEvent actionEvent){

    }

    public void updateDeveloper(ActionEvent actionEvent){

    }

    public void deleteDeveloper(ActionEvent actionEvent){

    }

    public void addUser(ActionEvent actionEvent){

    }

    public void updateUser(ActionEvent actionEvent){

    }

    public void deleteUser(ActionEvent actionEvent){

    }

    public void addAdmin(ActionEvent actionEvent){

    }

    public void updateAdmin(ActionEvent actionEvent){

    }

    public void deleteAdmin(ActionEvent actionEvent){

    }

    public void searchVG(ActionEvent actionEvent) {}

    public void searchDV(ActionEvent actionEvent) {}

    public void searchUsers(ActionEvent actionEvent) {}

    public void searchAdmins(ActionEvent actionEvent) {}

    private void configureLists(){
        VGListView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(VideoGame item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    UIControl.loadImage(imageView, item.getImageLink(), 50, 50);
                    setGraphic(imageView);
                    setText(item.getName());
                    setFont(Font.font(14));
                }
            }
        });

        DVListView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Developer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    UIControl.loadImage(imageView, item.getIconLink(), 50, 50);
                    setGraphic(imageView);
                    setText(item.getName());
                    setFont(Font.font(14));
                }
            }
        });

        userListView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(UserAccount item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    UIControl.loadImage(imageView, item.getAvatarLink(), 50, 50);
                    setGraphic(imageView);
                    setText(item.getUsername());
                    setFont(Font.font(14));
                }
            }
        });

        adminListView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(AdminAccount item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView imageView=new ImageView();
                    try {
                        imageView.setImage(new Image(new FileInputStream("resources/img/admin.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                    setText(item.getUsername());
                    setFont(Font.font(14));
                }
            }
        });
    }
}
