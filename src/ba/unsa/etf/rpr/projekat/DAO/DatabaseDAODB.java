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

            getNewVideoGameIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM video_game");
            getNewDeveloperIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM developer");
            getNewUserIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM user_account");
            getNewAdminIdQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM admin_account");

            addVideoGameQuery = conn.prepareStatement("INSERT INTO video_game VALUES(?,?,?,?,?)");
            addDeveloperQuery = conn.prepareStatement("INSERT INTO developer VALUES(?,?,?,?)");
            addUserQuery = conn.prepareStatement("INSERT INTO user_account VALUES(?,?,?,?)");
            addAdminQuery = conn.prepareStatement("INSERT INTO admin_account VALUES(?,?,?)");

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
        ObservableList<VideoGame> videoGames = FXCollections.observableArrayList();
        try {
            ResultSet rs = getVideoGamesQuery.executeQuery();
            while (rs.next()) {
                VideoGame videoGame = new VideoGame(rs.getInt(1),
                        rs.getString(2),
                        getDeveloperById(rs.getInt(3)),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate());
                videoGames.add(videoGame);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return videoGames;
    }

    @Override
    public void addVideoGame(VideoGame videoGame) {
        try {
            if (getDeveloperById(videoGame.getDeveloper().getId()) == null) {
                addDeveloper(videoGame.getDeveloper());
            }
            ResultSet rs = getNewVideoGameIdQuery.executeQuery();
            int id = 0;
            if (rs.next()) id = rs.getInt(1);
            videoGame.setId(id);
            addVideoGameQuery.setInt(1, videoGame.getId());
            addVideoGameQuery.setString(2, videoGame.getName());
            addVideoGameQuery.setInt(3, videoGame.getDeveloper().getId());
            addVideoGameQuery.setString(4, videoGame.getDescription());
            addVideoGameQuery.setDate(5, Date.valueOf(videoGame.getReleaseDate()));
            addVideoGameQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }

    }

    @Override
    public void removeVideoGame(VideoGame videoGame) {
        try {
            removeVideoGameQuery.setInt(1, videoGame.getId());
            removeVideoGameQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void updateVideoGame(VideoGame videoGame) {
        try {
            updateVideoGameQuery.setInt(5, videoGame.getId());
            updateVideoGameQuery.setString(1, videoGame.getName());
            updateVideoGameQuery.setInt(2, videoGame.getDeveloper().getId());
            updateVideoGameQuery.setString(3, videoGame.getDescription());
            updateVideoGameQuery.setDate(4, Date.valueOf(videoGame.getReleaseDate()));
            updateVideoGameQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public VideoGame getVideoGameById(int id) {
        VideoGame videoGame = null;
        try {
            getVideoGameByIdQuery.setInt(1, id);
            ResultSet rs = getVideoGameByIdQuery.executeQuery();
            while (rs.next()) {
                videoGame = new VideoGame(rs.getInt(1),
                        rs.getString(2),
                        getDeveloperById(rs.getInt(3)),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return videoGame;
    }

    @Override
    public ObservableList<Developer> getDevelopers() {
        ObservableList<Developer> developers = FXCollections.observableArrayList();
        try {
            ResultSet rs = getDevelopersQuery.executeQuery();
            while (rs.next()) {
                Developer developer = new Developer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                developers.add(developer);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return developers;
    }

    @Override
    public void addDeveloper(Developer developer) {
        try {
            ResultSet rs = getNewDeveloperIdQuery.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            developer.setId(id);
            addDeveloperQuery.setInt(1, developer.getId());
            addDeveloperQuery.setString(2, developer.getName());
            addDeveloperQuery.setString(3, developer.getDescription());
            addDeveloperQuery.setString(4, developer.getIconLink());
            addDeveloperQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void removeDeveloper(Developer developer) {
        try {
            removeDeveloperQuery.setInt(1, developer.getId());
            removeDeveloperQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void updateDeveloper(Developer developer) {
        try {
            updateDeveloperQuery.setInt(4, developer.getId());
            updateDeveloperQuery.setString(1, developer.getName());
            updateDeveloperQuery.setString(2, developer.getDescription());
            updateDeveloperQuery.setString(3, developer.getIconLink());
            updateDeveloperQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public Developer getDeveloperById(int id) {
        Developer developer = null;
        try {
            getDeveloperByIdQuery.setInt(1, id);
            ResultSet rs = getDeveloperByIdQuery.executeQuery();
            while (rs.next()) {
                developer = new Developer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return developer;
    }

    @Override
    public ObservableList<UserAccount> getUsers() {
        ObservableList<UserAccount> users = FXCollections.observableArrayList();
        try {
            ResultSet rs = getUsersQuery.executeQuery();
            while (rs.next()) {
                UserAccount userAccount = new UserAccount(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                users.add(userAccount);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return users;
    }

    @Override
    public void addUser(UserAccount userAccount) {
        try {
            ResultSet rs = getNewUserIdQuery.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            userAccount.setId(id);
            addUserQuery.setInt(1, userAccount.getId());
            addUserQuery.setString(2, userAccount.getUsername());
            addUserQuery.setString(3, userAccount.getPassword());
            addUserQuery.setString(4, userAccount.getAvatarLink());
            addUserQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void removeUser(UserAccount userAccount) {
        try {
            removeUserQuery.setInt(1, userAccount.getId());
            removeUserQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void updateUser(UserAccount userAccount) {
        try {
            updateUserQuery.setInt(4, userAccount.getId());
            updateUserQuery.setString(1, userAccount.getUsername());
            updateUserQuery.setString(2, userAccount.getPassword());
            updateUserQuery.setString(3, userAccount.getAvatarLink());
            updateUserQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public UserAccount getUserById(int id) {
        UserAccount userAccount=null;
        try {
            getUserByIdQuery.setInt(1, id);
            ResultSet rs = getUserByIdQuery.executeQuery();
            while (rs.next()) {
                userAccount = new UserAccount(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return userAccount;
    }

    @Override
    public ObservableList<AdminAccount> getAdmins() {
        ObservableList<AdminAccount> admins = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAdminsQuery.executeQuery();
            while (rs.next()) {
                AdminAccount adminAccount = new AdminAccount(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                admins.add(adminAccount);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return admins;
    }

    @Override
    public void addAdmin(AdminAccount adminAccount) {
        try {
            ResultSet rs = getNewAdminIdQuery.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            adminAccount.setId(id);
            addUserQuery.setInt(1, adminAccount.getId());
            addUserQuery.setString(2, adminAccount.getUsername());
            addUserQuery.setString(3, adminAccount.getPassword());
            addUserQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void removeAdmin(AdminAccount adminAccount) {
        try {
            removeAdminQuery.setInt(1, adminAccount.getId());
            removeAdminQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public void updateAdmin(AdminAccount adminAccount) {
        try {
            updateAdminQuery.setInt(4, adminAccount.getId());
            updateAdminQuery.setString(1, adminAccount.getUsername());
            updateAdminQuery.setString(2, adminAccount.getPassword());
            updateAdminQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
    }

    @Override
    public AdminAccount getAdminById(int id) {
        AdminAccount adminAccount=null;
        try {
            getAdminByIdQuery.setInt(1, id);
            ResultSet rs = getAdminByIdQuery.executeQuery();
            while (rs.next()) {
                 adminAccount = new AdminAccount(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error while executing query");
        }
        return adminAccount;
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
