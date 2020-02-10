package ba.unsa.etf.rpr.projekat;

public abstract class Account {
    private Integer id;
    private String username;
    private String password;

    public Account(){
        super();
    }

    public Account(int id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return getUsername();
    }
}
