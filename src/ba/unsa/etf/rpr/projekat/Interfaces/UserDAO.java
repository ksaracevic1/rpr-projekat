package ba.unsa.etf.rpr.projekat.Interfaces;

import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import javafx.collections.ObservableList;

public interface UserDAO {
    ObservableList<UserAccount> getUsers();
    void addUser(UserAccount userAccount);
    void removeUser(UserAccount userAccount);
    void updateUser(UserAccount userAccount);
    UserAccount getUserById(int id);
    void close();
}
