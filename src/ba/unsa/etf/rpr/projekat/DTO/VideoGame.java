package ba.unsa.etf.rpr.projekat.DTO;

import ba.unsa.etf.rpr.projekat.DTO.Developer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class VideoGame {

    private Integer id;
    private Developer developer;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleObjectProperty<LocalDate> releaseDate;

    public VideoGame(Integer id, String name, Developer developer, String description, LocalDate releaseDate){
        this.id=id;
        this.developer=developer;
        this.name=new SimpleStringProperty(name);
        this.description=new SimpleStringProperty(description);
        this.releaseDate=new SimpleObjectProperty<>(releaseDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDate getReleaseDate() {
        return releaseDate.get();
    }

    public SimpleObjectProperty<LocalDate> releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate.set(releaseDate);
    }
}
