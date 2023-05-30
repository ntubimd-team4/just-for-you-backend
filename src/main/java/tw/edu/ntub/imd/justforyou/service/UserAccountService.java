package tw.edu.ntub.imd.justforyou.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;

public interface UserAccountService extends BaseService<UserAccountBean, String>, UserDetailsService {

    void updateAvailable(String userId);
}