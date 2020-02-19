package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import ba.unsa.etf.rpr.projekat.UIControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginController extends Controller {
    public TextField usernameField;
    public PasswordField passwordField;

    public Button loginButton, exitButton, registerButton;

    public Label errorLabel;
    public ChoiceBox<String> languageBox;

    private DatabaseDAO dao = null;
    private ArrayList<Account> accounts = null;
    private ResourceBundle bundle = ResourceBundle.getBundle("Language");
    private ObservableList<String> languages =
            FXCollections.observableArrayList(bundle.getString("bosnian"), bundle.getString("english"));

    public LoginController(DatabaseDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        if (dao != null) dao.close();
        dao = new DatabaseDAODB();
        accounts = new ArrayList<Account>();
        accounts.addAll(dao.getAdmins());
        accounts.addAll(dao.getUsers());
        languageBox.setItems(languages);
    }

    public void loginClick(ActionEvent actionEvent) {
        errorLabel.setText("");
        String un = usernameField.getText();
        String pass = passwordField.getText();
        Boolean exists = false;
        if (!un.equals("") && !pass.equals("")) {
            for (int i = 0; i < accounts.size(); i++) {
                Account currentAccount = accounts.get(i);
                if (un.equals(currentAccount.getUsername())) {
                    exists = true;
                    if (currentAccount.getPassword().equals(pass)) {
                        openMain(currentAccount);
                        Stage stage = (Stage) exitButton.getScene().getWindow();
                        stage.close();
                    } else {
                        errorLabel.setText("Incorrect password");
                    }
                    break;
                }
            }
            if (!exists) {
                errorLabel.setText("Incorrect username");
            }
        } else {
            errorLabel.setText("Username or password field empty");
        }
    }

    public void registerClick(ActionEvent actionEvent) {
        UIControl.openWindow(getClass(), new RegistrationController(dao, accounts), ResourceBundle.getBundle("Language"), "register.fxml");
    }

    public void exitClick(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void switchLanguage(ActionEvent actionEvent) {
        int pos = languages.indexOf(languageBox.getValue());
        if (pos == 0) {
            Locale.setDefault(new Locale("bs_BA"));
        } else if (pos == 1) {
            Locale.setDefault(new Locale("en_US", "US"));
        }
        languages.clear();
        languages.add(bundle.getString("bosnian"));
        languages.add(bundle.getString("english"));
        languageBox.setItems(languages);
        Scene scene = usernameField.getScene();
        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        loader.setController(new LoginController(dao));
        try {
            scene.setRoot(loader.load());
        } catch (IOException | NullPointerException e) {
        }
    }

    public void openMain(Account account) {
        String viewType;
        if (account instanceof AdminAccount) {
            viewType = "adminView.fxml";
        } else {
            viewType = "userView.fxml";
        }
        UIControl.openWindow(getClass(), new MainController(dao, account), ResourceBundle.getBundle("Language"), viewType);
    }
}
