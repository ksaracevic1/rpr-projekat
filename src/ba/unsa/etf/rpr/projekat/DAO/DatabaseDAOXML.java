package ba.unsa.etf.rpr.projekat.DAO;

import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseDAOXML implements DatabaseDAO {

    private ArrayList<VideoGame> videoGames = new ArrayList<>();
    private ArrayList<Developer> developers = new ArrayList<>();
    private ArrayList<UserAccount> userAccounts = new ArrayList<>();
    private ArrayList<AdminAccount> adminAccounts = new ArrayList<>();

    public DatabaseDAOXML() {
        read();
    }

    private void read() {
        videoGames.clear();
        developers.clear();
        userAccounts.clear();
        adminAccounts.clear();
        try {
            XMLDecoder decoder = new XMLDecoder(new FileInputStream("resources/xml/videoGames.xml"));
            videoGames = (ArrayList<VideoGame>) decoder.readObject();
            decoder.close();

            decoder = new XMLDecoder(new FileInputStream("resources/xml/developers.xml"));
            developers = (ArrayList<Developer>) decoder.readObject();
            decoder.close();

            decoder = new XMLDecoder(new FileInputStream("resources/xml/userAccounts.xml"));
            userAccounts = (ArrayList<UserAccount>) decoder.readObject();
            decoder.close();

            decoder = new XMLDecoder(new FileInputStream("resources/xml/adminAccounts.xml"));
            adminAccounts = (ArrayList<AdminAccount>) decoder.readObject();
            decoder.close();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to read file");
        }
    }

    private void write() {
        try {
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("resources/xml/videoGames.xml"));
            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });
            encoder.writeObject(videoGames);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("resources/xml/developers.xml"));
            encoder.writeObject(developers);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("resources/xml/userAccounts.xml"));
            encoder.writeObject(userAccounts);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("resources/xml/adminAccounts.xml"));
            encoder.writeObject(adminAccounts);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

    @Override
    public ObservableList<VideoGame> getVideoGames() {
        return FXCollections.observableArrayList(videoGames);
    }

    @Override
    public void addVideoGame(VideoGame videoGame) {
        if (getDeveloperById(videoGame.getDeveloper().getId()) == null) {
            addDeveloper(videoGame.getDeveloper());
        }
        int maxId = 0;
        for (VideoGame v : videoGames)
            if (v.getId() > maxId) maxId = v.getId();
        videoGame.setId(maxId + 1);
        videoGames.add(videoGame);
        write();
    }

    @Override
    public void removeVideoGame(VideoGame videoGame) {
        for (int i = 0; i < videoGames.size(); i++)
            if (videoGames.get(i).getId().equals(videoGame.getId())) {
                videoGames.remove(i);
                break;
            }
        write();
    }

    @Override
    public void updateVideoGame(VideoGame videoGame) {
        for (int i = 0; i < videoGames.size(); i++)
            if (videoGames.get(i).getId().equals(videoGame.getId()))
                videoGames.set(i, videoGame);
        write();
    }

    @Override
    public VideoGame getVideoGameById(int id) {
        VideoGame videoGame = null;
        for (VideoGame v : videoGames) {
            if (v.getId().equals(id)) {
                videoGame = v;
            }
        }
        return videoGame;
    }

    @Override
    public ObservableList<VideoGame> getVideoGameByName(String name) {
        ObservableList<VideoGame> videoGames = getVideoGames();
        int size = videoGames.size();
        for (int i = 0; i < size; i++) {
            if (videoGames.get(i).getName().contains(name)) {
                videoGames.remove(i);
                i--;
                size--;
            }
        }
        return videoGames;
    }

    @Override
    public ObservableList<VideoGame> getVideoGameByGenre(String genre) {
        ObservableList<VideoGame> videoGames = getVideoGames();
        int size = videoGames.size();
        for (int i = 0; i < size; i++) {
            if (videoGames.get(i).getGenre().contains(genre)) {
                videoGames.remove(i);
                i--;
                size--;
            }
        }
        return videoGames;
    }

    @Override
    public ObservableList<VideoGame> getVideoGameByDeveloper(String developer) {
        ObservableList<VideoGame> videoGames = getVideoGames();
        int size = videoGames.size();
        for (int i = 0; i < size; i++) {
            if (videoGames.get(i).getDeveloper().getName().contains(developer)) {
                videoGames.remove(i);
                i--;
                size--;
            }
        }
        return videoGames;
    }

    @Override
    public ObservableList<Developer> getDevelopers() {
        return FXCollections.observableArrayList(developers);
    }

    @Override
    public void addDeveloper(Developer developer) {
        int maxId = 0;
        for (Developer d : developers)
            if (d.getId() > maxId) maxId = d.getId();
        developer.setId(maxId + 1);
        developers.add(developer);
        write();
    }

    @Override
    public void removeDeveloper(Developer developer) {
        for (int i = 0; i < developers.size(); i++)
            if (developers.get(i).getId().equals(developer.getId())) {
                developers.remove(i);
                break;
            }
        write();
    }

    @Override
    public void updateDeveloper(Developer developer) {
        for (int i = 0; i < developers.size(); i++)
            if (developers.get(i).getId().equals(developer.getId()))
                developers.set(i, developer);
        write();
    }

    @Override
    public Developer getDeveloperById(int id) {
        Developer developer = null;
        for (Developer d : developers) {
            if (d.getId().equals(id)) {
                developer = d;
                break;
            }
        }
        return developer;
    }

    @Override
    public ObservableList<Developer> getDeveloperByName(String name) {
        ObservableList<Developer> developers = getDevelopers();
        int size = developers.size();
        for (int i = 0; i < size; i++) {
            if (developers.get(i).getName().contains(name)) {
                developers.remove(i);
                i--;
                size--;
            }
        }
        return developers;
    }

    @Override
    public ObservableList<UserAccount> getUsers() {
        return FXCollections.observableArrayList(userAccounts);
    }

    @Override
    public void addUser(UserAccount userAccount) {
        int maxId = 0;
        for (UserAccount u : userAccounts)
            if (u.getId() > maxId) maxId = u.getId();
        userAccount.setId(maxId + 1);
        userAccounts.add(userAccount);
        write();
    }

    @Override
    public void removeUser(UserAccount userAccount) {
        for (int i = 0; i < userAccounts.size(); i++)
            if (userAccounts.get(i).getId().equals(userAccount.getId())) {
                userAccounts.remove(i);
                break;
            }
        write();
    }

    @Override
    public void updateUser(UserAccount userAccount) {
        for (int i = 0; i < userAccounts.size(); i++)
            if (userAccounts.get(i).getId().equals(userAccount.getId()))
                userAccounts.set(i, userAccount);
        write();
    }

    @Override
    public UserAccount getUserById(int id) {
        UserAccount userAccount = null;
        for (UserAccount ua : userAccounts) {
            if (ua.getId().equals(id)) {
                userAccount = ua;
            }
        }
        return userAccount;
    }

    @Override
    public ObservableList<AdminAccount> getAdmins() {
        return FXCollections.observableArrayList(adminAccounts);
    }

    @Override
    public void addAdmin(AdminAccount adminAccount) {
        int maxId = 0;
        for (AdminAccount a : adminAccounts)
            if (a.getId() > maxId) maxId = a.getId();
        adminAccount.setId(maxId + 1);
        adminAccounts.add(adminAccount);
        write();
    }

    @Override
    public void removeAdmin(AdminAccount adminAccount) {
        for (int i = 0; i < adminAccounts.size(); i++)
            if (adminAccounts.get(i).getId().equals(adminAccount.getId())) {
                adminAccounts.remove(i);
                break;
            }
        write();
    }

    @Override
    public void updateAdmin(AdminAccount adminAccount) {
        for (int i = 0; i < adminAccounts.size(); i++)
            if (adminAccounts.get(i).getId().equals(adminAccount.getId()))
                adminAccounts.set(i, adminAccount);
        write();
    }

    @Override
    public AdminAccount getAdminById(int id) {
        AdminAccount adminAccount = null;
        for (AdminAccount ua : adminAccounts) {
            if (ua.getId().equals(id)) {
                adminAccount = ua;
            }
        }
        return adminAccount;
    }

    @Override
    public void close() {

    }
}
