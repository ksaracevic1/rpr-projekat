package ba.unsa.etf.rpr.projekat.Interfaces;

import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import javafx.collections.ObservableList;

public interface DatabaseDAO {
    ObservableList<VideoGame> getVideoGames();
    void addVideoGame(VideoGame videoGame);
    void removeVideoGame(VideoGame videoGame);
    void updateVideoGame(VideoGame videoGame);
    VideoGame getVideoGameById(int id);

    ObservableList<Developer> getDevelopers();
    void addDeveloper(Developer developer);
    void removeDeveloper(Developer developer);
    void updateDeveloper(Developer developer);
    Developer getDeveloperById(int id);

    ObservableList<UserAccount> getUsers();
    void addUser(UserAccount userAccount);
    void removeUser(UserAccount userAccount);
    void updateUser(UserAccount userAccount);
    UserAccount getUserById(int id);

    ObservableList<AdminAccount> getAdmins();
    void addAdmin(AdminAccount adminAccount);
    void removeAdmin(AdminAccount adminAccount);
    void updateAdmin(AdminAccount adminAccount);
    AdminAccount getAdminById(int id);
    void close();
}
