package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAO.DatabaseDAODB;
import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController{
    public TextField usernameField;
    public PasswordField passwordField;

    public Button okButton;
    public Button exitButton;

    public Label errorLabel;

    private DatabaseDAO dao = null;
    private ArrayList<Account> accounts=null;

    @FXML
    public void initialize(){
        if (dao != null) dao.close();
        dao = new DatabaseDAODB();
        accounts=new ArrayList<Account>();
        accounts.addAll(dao.getAdmins());
        accounts.addAll(dao.getUsers());
    }

    public void okClick(ActionEvent actionEvent){
        errorLabel.setText("");
        String un=usernameField.getText();
        String pass=passwordField.getText();
        Boolean exists=false;
        if(!un.equals("") && !pass.equals("")) {
            for (int i = 0; i < accounts.size(); i++) {
                Account currentAccount=accounts.get(i);
                if(un.equals(currentAccount.getUsername())){
                    exists=true;
                    if(currentAccount.getPassword().equals(pass)){
                        openMain(currentAccount);
                        Stage stage = (Stage) exitButton.getScene().getWindow();
                        stage.close();
                    }
                    else
                    {
                        errorLabel.setText("Incorrect password");
                    }
                    break;
                }
            }
            if(!exists) {
                errorLabel.setText("Incorrect username");
            }
        }
        else{
            errorLabel.setText("Username or password field empty");
        }
    }

    public void exitClick(ActionEvent actionEvent){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void openMain(Account account){
        String viewType;
        if(account instanceof AdminAccount){
            viewType="adminView.fxml";
        } else {
            viewType="userView.fxml";
        }
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle=ResourceBundle.getBundle("Language");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+viewType));
            MainController mainController = new MainController(dao,account);
            loader.setController(mainController);
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
