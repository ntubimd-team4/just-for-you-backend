package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;
import java.util.List;
@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String> {
    @Query("FROM UserAccount")
    List<UserAccount> findData();
}