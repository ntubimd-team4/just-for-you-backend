package tw.edu.ntub.imd.justforyou.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;

public interface UserAccountService extends BaseService<UserAccountBean, String>, UserDetailsService {
}