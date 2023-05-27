package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.justforyou.service.transformer.UserAccountTransformer;

@Component
public class UserAccountTransformerImpl implements UserAccountTransformer {
    @NonNull
    @Override
    public UserAccount transferToEntity(@NonNull UserAccountBean userAccountBean) {
        return JavaBeanUtils.copy(userAccountBean, new UserAccount());
    }

    @NonNull
    @Override
    public UserAccountBean transferToBean(@NonNull UserAccount userAccount) {
        return JavaBeanUtils.copy(userAccount, new UserAccountBean());
    }
}