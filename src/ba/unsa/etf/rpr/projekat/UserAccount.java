package ba.unsa.etf.rpr.projekat;

public class UserAccount extends Account {

    private String avatarLink;

    public UserAccount(int id, String username, String password, String avatarLink) {
        super(id, username, password);
        this.avatarLink=avatarLink;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }


}