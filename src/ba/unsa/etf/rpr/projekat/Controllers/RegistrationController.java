package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RegistrationController extends Controller {

    public TextField usernameField, avatarField;
    public PasswordField  passwordField;

    private DatabaseDAO dao;
    private ArrayList<Account> accounts;

    public RegistrationController(DatabaseDAO dao, ArrayList<Account> accounts){
        this.dao=dao;
        this.accounts=accounts;
    }

    @FXML
    public void initialize(){

    }

    public void registerClick(ActionEvent actionEvent){
        usernameField.getStyleClass().removeAll();
        passwordField.getStyleClass().removeAll();
        Boolean b1=usernameEmpty(),b2=passwordEmpty();
        if(b1){
            usernameField.getStyleClass().add("fieldIncorrect");
        } else{
            usernameField.getStyleClass().add("fieldCorrect");
        }
        if(b2){
            passwordField.getStyleClass().add("fieldIncorrect");
        } else{
            passwordField.getStyleClass().add("fieldCorrect");
        }
        if(!b1 && !b2){
            UserAccount ua=new UserAccount(-1,usernameField.getText(),passwordField.getText(),avatarField.getText());
            dao.addUser(ua);
            Stage stage=(Stage)usernameField.getScene().getWindow();
            accounts.add(ua);
            stage.close();
        }
    }

    private Boolean usernameEmpty(){
        return usernameField.getText().equals("");
    }

    private Boolean passwordEmpty(){
        return passwordField.getText().equals("");
    }
}
