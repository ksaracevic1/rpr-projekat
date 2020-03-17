package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserFormController extends Controller {
    private UserAccount userAccount;
    private DatabaseDAO dao;
    private ObservableList<UserAccount> userAccounts;
    public TextField usernameField, passwordField, imageField;

    public UserFormController(UserAccount userAccount, DatabaseDAO dao, ObservableList<UserAccount> userAccounts) {
        this.userAccount = userAccount;
        this.dao = dao;
        this.userAccounts = userAccounts;
    }

    @FXML
    public void initialize(){

    }
}
