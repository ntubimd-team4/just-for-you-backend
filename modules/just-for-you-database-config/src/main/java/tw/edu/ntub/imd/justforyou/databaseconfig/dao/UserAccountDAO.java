package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;

@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String> {
}