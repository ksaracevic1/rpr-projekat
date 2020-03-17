package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.ObservableList;

public class DevFormController extends Controller {
    private Developer developer;
    private ObservableList<Developer> developers;
    private DatabaseDAO dao;

    public DevFormController(Developer developer, ObservableList<Developer> developers, DatabaseDAO dao) {
        this.developer = developer;
        this.developers = developers;
        this.dao = dao;
    }
}
