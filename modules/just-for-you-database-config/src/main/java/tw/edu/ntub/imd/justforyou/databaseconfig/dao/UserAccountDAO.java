package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;

import java.util.List;

@Repository
public interface UserAccountDAO extends BaseDAO<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {
    @Query("FROM UserAccount u WHERE u.role = 0 OR u.role = 1")
    List<UserAccount> findByTeacher();

    @Query("SELECT u.userId FROM UserAccount u WHERE u.available = true AND u.role = 0")
    String[] findByCaseManagement();

    @Query("SELECT u.userId FROM UserAccount u WHERE u.available = true AND u.role = 0 OR u.available = true AND u.role = 1")
    String[] findByAllTeacher();
}