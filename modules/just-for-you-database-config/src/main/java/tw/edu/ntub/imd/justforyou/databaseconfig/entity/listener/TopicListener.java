package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Topic;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class TopicListener {
    @PrePersist
    public void preSave(Topic topic) {
        if (topic.getCreateId() == null) {
            topic.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (topic.getCreateTime() == null) {
            topic.setCreateTime(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Topic topic) {
        topic.setModifyId(SecurityUtils.getLoginUserAccount());
        topic.setModifyTime(LocalDateTime.now());
    }
}