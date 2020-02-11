package ba.unsa.etf.rpr.projekat.Interfaces;

import ba.unsa.etf.rpr.projekat.DTO.Developer;
import javafx.collections.ObservableList;

public interface DeveloperDAO {
    ObservableList<Developer> getDeveloper();
    void addDeveloper(Developer developer);
    void removeDeveloper(Developer developer);
    void updateDeveloper(Developer developer);
    Developer getDeveloperById(int id);
    void close();
}
