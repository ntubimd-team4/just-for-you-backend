package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MusicRecommendListener {
    @PrePersist
    public void preSave(MusicRecommend musicRecommend) {
        if (musicRecommend.getCreateId() == null) {
            musicRecommend.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (musicRecommend.getCreateTime() == null) {
            musicRecommend.setCreateTime(LocalDateTime.now());
        }
        if (musicRecommend.getCollection() == null) {
            musicRecommend.setCollection(false);
        }
    }

    @PreUpdate
    public void preUpdate(MusicRecommend musicRecommend) {
        musicRecommend.setModifyId(SecurityUtils.getLoginUserAccount());
        musicRecommend.setModifyTime(LocalDateTime.now());
    }
}