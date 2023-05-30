package tw.edu.ntub.imd.justforyou.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dto.Pager;

import java.util.List;


public interface UserAccountService extends BaseService<UserAccountBean, String>, UserDetailsService {
    List<UserAccountBean> searchData(String type, Pager pager);

    List<UserAccountBean> searchKeywordList(String userId, String userName, String department, Pager pager);

    int getTotalPage(String type, int count);

    int getKeywordListTotalPage(String userId, String userName, String department, int count);

    void updateAvailable(String userId);
}