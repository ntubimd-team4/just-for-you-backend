package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class EmotionListener {
    @PrePersist
    public void preSave(Emotion emotion) {
        if (emotion.getCreateId() == null) {
            emotion.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (emotion.getCreateTime() == null) {
            emotion.setCreateTime(LocalDateTime.now());
        }
        if (emotion.getAvailable() == null) {
            emotion.setAvailable(true);
        }
    }

    @PreUpdate
    public void preUpdate(Emotion emotion) {
        emotion.setModifyId(SecurityUtils.getLoginUserAccount());
        emotion.setModifyTime(LocalDateTime.now());
    }
}