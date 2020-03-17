package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminFormController extends Controller {
    private AdminAccount adminAccount;
    private DatabaseDAO dao;
    private ObservableList<AdminAccount> adminAccounts;
    public TextField usernameField, passwordField;

    public AdminFormController(AdminAccount adminAccount, DatabaseDAO dao, ObservableList<AdminAccount> adminAccounts) {
        this.adminAccount = adminAccount;
        this.dao = dao;
        this.adminAccounts = adminAccounts;
    }

    @FXML
    public void initialize(){

    }

    public void okClick(ActionEvent actionEvent){

    }

    public void cancelClick(ActionEvent actionEvent){

    }
}
