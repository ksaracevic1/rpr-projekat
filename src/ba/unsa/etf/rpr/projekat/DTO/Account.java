package ba.unsa.etf.rpr.projekat.DTO;

import javafx.beans.property.SimpleStringProperty;

public abstract class Account {
    private Integer id;
    private SimpleStringProperty username;
    private SimpleStringProperty password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Account(){
        super();
    }

    public Account(int id, String username, String password){
        this.id=id;
        this.username=new SimpleStringProperty(username);
        this.password=new SimpleStringProperty(password);
    }


    @Override
    public String toString(){
        return getUsername();
    }
}
