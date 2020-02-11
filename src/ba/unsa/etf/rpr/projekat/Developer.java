package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;

public class Developer {
    private Integer id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty iconLink;

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
        return iconLink.get();
    }

    public SimpleStringProperty iconLinkProperty() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink.set(iconLink);
    }

    @Override
    public String toString(){
        return getName();
    }
}
