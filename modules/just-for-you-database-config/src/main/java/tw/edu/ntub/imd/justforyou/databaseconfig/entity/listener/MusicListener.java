package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MusicListener {
    @PrePersist
    public void preSave(Music music) {
        if (music.getCreateId() == null) {
            music.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (music.getCreateTime() == null) {
            music.setCreateTime(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Music music) {
        music.setModifyId(SecurityUtils.getLoginUserAccount());
        music.setModifyTime(LocalDateTime.now());
    }
}