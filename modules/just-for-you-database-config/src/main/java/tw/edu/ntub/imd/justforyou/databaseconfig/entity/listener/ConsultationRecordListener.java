package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.ConsultationRecord;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ConsultationRecordListener {
    @PrePersist
    public void preSave(ConsultationRecord consultationRecord) {
        if (consultationRecord.getCreateId() == null) {
            consultationRecord.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (consultationRecord.getCreateTime() == null) {
            consultationRecord.setCreateTime(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(ConsultationRecord consultationRecord) {
        consultationRecord.setModifyId(SecurityUtils.getLoginUserAccount());
        consultationRecord.setModifyTime(LocalDateTime.now());
    }
}
