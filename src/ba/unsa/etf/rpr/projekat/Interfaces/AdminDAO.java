package ba.unsa.etf.rpr.projekat.Interfaces;

import ba.unsa.etf.rpr.projekat.DTO.AdminAccount;
import javafx.collections.ObservableList;

public interface AdminDAO {
    ObservableList<AdminAccount> getAdmin();
    void addAdmin(AdminAccount adminAccount);
    void removeAdmin(AdminAccount adminAccount);
    void updateAdmin(AdminAccount adminAccount);
    AdminAccount getAdminById(int id);
    void close();
}
