package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.Account;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.event.ActionEvent;

public class MainController {

    DatabaseDAO dao;
    Account accountInUse;

    public MainController(DatabaseDAO dao, Account accountInUse) {
        this.dao=dao;
        this.accountInUse=accountInUse;
    }

    public void switchDb(ActionEvent actionEvent){

    }

    public void switchXml(ActionEvent actionEvent){

    }

    public void clickAbout(ActionEvent actionEvent){

    }

    public void clickLogout(ActionEvent actionEvent){

    }
}
