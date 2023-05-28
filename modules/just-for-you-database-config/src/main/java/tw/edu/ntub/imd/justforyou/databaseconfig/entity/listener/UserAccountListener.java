package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class UserAccountListener {
    @PrePersist
    public void preSave(UserAccount userAccount) {
        LocalDateTime now = LocalDateTime.now();
        if (userAccount.getCreateTime() == null) {
            userAccount.setCreateTime(now);
        }
    }

    @PreUpdate
    public void preUpdate(UserAccount userAccount) {
        userAccount.setModifyId(SecurityUtils.getLoginUserAccount());
        userAccount.setModifyTime(LocalDateTime.now());
    }
}