package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MusicEmotionListener {
    @PrePersist
    public void preSave(MusicEmotion musicEmotion) {
        if (musicEmotion.getAvailable() == null) {
            musicEmotion.setAvailable(true);
        }
        if (StringUtils.isBlank(musicEmotion.getCreateId())) {
            musicEmotion.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (musicEmotion.getCreateTime() == null) {
            musicEmotion.setCreateTime(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(MusicEmotion musicEmotion) {
        musicEmotion.setModifyId(SecurityUtils.getLoginUserAccount());
        musicEmotion.setModifyTime(LocalDateTime.now());
    }
}
