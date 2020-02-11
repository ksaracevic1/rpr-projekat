package ba.unsa.etf.rpr.projekat.DAO;

import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import ba.unsa.etf.rpr.projekat.DTO.Developer;
import ba.unsa.etf.rpr.projekat.DTO.UserAccount;
import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import ba.unsa.etf.rpr.projekat.Interfaces.DatabaseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DatabaseDAODB implements DatabaseDAO {

    private Connection conn;
    private PreparedStatement getVideoGamesQuery, addVideoGameQuery, removeVideoGameQuery, updateVideoGameQuery, getVideoGameByIdQuery,
            getDevelopersQuery, addDeveloperQuery, removeDeveloperQuery, updateDeveloperQuery, getDeveloperByIdQuery,
            getUsersQuery, addUserQuery, removeUserQuery, updateUserQuery, getUserByIdQuery,
            getAdminsQuery, addAdminQuery, removeAdminQuery, updateAdminQuery, getAdminByIdQuery,
            getNewVideoGameIdQuery, getNewAdminIdQuery, getNewUserIdQuery, getNewDeveloperIdQuery;

    DatabaseDAODB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/db/database.db");
            PreparedStatement tableAmountQuery = conn.prepareStatement("SELECT count(*) FROM sqlite_master WHERE type = 'table'");
            ResultSet resultSet = tableAmountQuery.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                Scanner input;
                try {
                    input = new Scanner(new FileInputStream("korisnici.sql"));
                    String sqlQuery = "";
                    while (input.hasNext()) {
                        sqlQuery += input.nextLine();
                        if (sqlQuery.length() > 1 && sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                            try {
                                Statement stmt = conn.createStatement();
                                stmt.execute(sqlQuery);
                                sqlQuery = "";
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    input.close();
                } catch (FileNotFoundException e) {
                    System.out.println("SQL file does not exist");
                }
            }

            getVideoGamesQuery = conn.prepareStatement("SELECT * FROM video_game");
            getDevelopersQuery = conn.prepareStatement("SELECT * FROM developer");
            getUsersQuery = conn.prepareStatement("SELECT * FROM user_account");
            getAdminsQuery = conn.prepareStatement("SELECT * FROM admin_account");

            getVideoGameByIdQuery = conn.prepareStatement("SELECT * FROM video_game WHERE id=?");
            getDeveloperByIdQuery = conn.prepareStatement("SELECT * FROM developer WHERE id=?");
            getUserByIdQuery = conn.prepareStatement("SELECT * FROM user_account WHERE id=?");
            getAdminByIdQuery = conn.prepareStatement("SELECT * FROM admin_account WHERE id=?");

            getNewVideoGameIdQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM video_game");
            getNewDeveloperIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM developer");
            getNewUserIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM user_account");
            getNewAdminIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM admin_account");

            addVideoGameQuery=conn.prepareStatement("INSERT INTO video_game VALUES(?,?,?,?,?)");
            addDeveloperQuery=conn.prepareStatement("INSERT INTO developer VALUES(?,?,?,?)");
            addUserQuery=conn.prepareStatement("INSERT INTO user_account VALUES(?,?,?,?)");
            addAdminQuery=conn.prepareStatement("INSERT INTO admin_account VALUES(?,?,?)");

            removeVideoGameQuery = conn.prepareStatement("DELETE FROM video_game WHERE id=?");
            removeDeveloperQuery = conn.prepareStatement("DELETE FROM developer WHERE id=?");
            removeUserQuery = conn.prepareStatement("DELETE FROM user_account WHERE id=?");
            removeAdminQuery = conn.prepareStatement("DELETE FROM admin_account WHERE id=?");

            updateVideoGameQuery = conn.prepareStatement("UPDATE video_game SET name=?, dev_id=?, description=?, release_date=? WHERE id=?");
            updateDeveloperQuery = conn.prepareStatement("UPDATE developer SET name=?, description=?, icon=? WHERE id=?");
            updateUserQuery = conn.prepareStatement("UPDATE user_account SET username=?, password=?, avatar=? WHERE id=?");
            updateAdminQuery = conn.prepareStatement("UPDATE admin_account SET username=?, password=? WHERE id=?");
        } catch (SQLException e) {
            System.out.println("Failed to prepare statement");
        }
    }

    @Override
    public ObservableList<VideoGame> getVideoGames() {
        ObservableList<VideoGame> videoGames= FXCollections.observableArrayList();
        try{
            ResultSet rs=getVideoGamesQuery.executeQuery();
            while(rs.next()){
                VideoGame videoGame=new VideoGame(rs.getInt(1),rs.getString(2),getDeveloperById(rs.getInt(3)),rs.getString(4),rs.getDate(5).toLocalDate());
                videoGames.add(videoGame);
            }
        } catch (SQLException e){
            System.out.println("Error while executing query");
        }
        return videoGames;
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
        Developer developer=null;
        try {
            getDeveloperByIdQuery.setInt(1,id);
            ResultSet rs=getDeveloperByIdQuery.executeQuery();
            while (rs.next()){
                developer=new Developer(rs.getInt(1),rs.getString(2),rs.getString(2),rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return developer;
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
