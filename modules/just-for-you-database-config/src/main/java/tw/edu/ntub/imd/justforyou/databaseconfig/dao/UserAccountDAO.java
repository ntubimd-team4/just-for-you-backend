package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;

@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {
}