package ba.unsa.etf.rpr.projekat.Interfaces;

import ba.unsa.etf.rpr.projekat.DTO.VideoGame;
import javafx.collections.ObservableList;

public interface VideoGameDAO {
    ObservableList<VideoGame> getVideoGames();
    void addVideoGame(VideoGame videoGame);
    void removeVideoGame(VideoGame videoGame);
    void updateVideoGame(VideoGame videoGame);
    VideoGame getVideoGameById(int id);
    void close();
}
