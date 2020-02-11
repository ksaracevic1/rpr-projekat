package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;

public class Developer {
    private Integer id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private String iconLink;

    public Developer(Integer id, String name, String description, String iconLink) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.iconLink = iconLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    @Override
    public String toString(){
        return getName();
    }
}
