package tw.edu.ntub.imd.justforyou.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dto.Pager;

import java.util.List;


public interface UserAccountService extends BaseService<UserAccountBean, String>, UserDetailsService {
    List<UserAccountBean> searchData(String type, Pager pager);

    void updateAvailable(String userId);

    int getCount(String type, int count);
}