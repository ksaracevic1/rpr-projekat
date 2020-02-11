package ba.unsa.etf.rpr.projekat.DAO;

import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.ObservableList;

public class DatabaseDAOXML implements DatabaseDAO {
    @Override
    public ObservableList<VideoGame> getVideoGames() {
        return null;
    }

    @Override
    public void addVideoGame(VideoGame videoGame) {

    }

    @Override
    public void removeVideoGame(VideoGame videoGame) {

    }

    @Override
    public void updateVideoGame(VideoGame videoGame) {

    }

    @Override
    public VideoGame getVideoGameById(int id) {
        return null;
    }

    @Override
    public ObservableList<Developer> getDeveloper() {
        return null;
    }

    @Override
    public void addDeveloper(Developer developer) {

    }

    @Override
    public void removeDeveloper(Developer developer) {

    }

    @Override
    public void updateDeveloper(Developer developer) {

    }

    @Override
    public Developer getDeveloperById(int id) {
        return null;
    }

    @Override
    public ObservableList<UserAccount> getUsers() {
        return null;
    }

    @Override
    public void addUser(UserAccount userAccount) {

    }

    @Override
    public void removeUser(UserAccount userAccount) {

    }

    @Override
    public void updateUser(UserAccount userAccount) {

    }

    @Override
    public UserAccount getUserById(int id) {
        return null;
    }

    @Override
    public ObservableList<AdminAccount> getAdmin() {
        return null;
    }

    @Override
    public void addAdmin(AdminAccount adminAccount) {

    }

    @Override
    public void removeAdmin(AdminAccount adminAccount) {

    }

    @Override
    public void updateAdmin(AdminAccount adminAccount) {

    }

    @Override
    public AdminAccount getAdminById(int id) {
        return null;
    }

    @Override
    public void close() {

    }
}
