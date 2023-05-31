package tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener;

import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class SummaryRecordListener {
    @PrePersist
    public void preSave(SummaryRecord summaryRecord) {
        LocalDateTime now = LocalDateTime.now();
        if (summaryRecord.getCreateId() == null) {
            summaryRecord.setCreateId(SecurityUtils.getLoginUserAccount());
        }
        if (summaryRecord.getCreateTime() == null) {
            summaryRecord.setCreateTime(now);
        }
        if (summaryRecord.getEstablishTime() == null) {
            summaryRecord.setEstablishTime(now);
        }
    }

    @PreUpdate
    public void preUpdate(SummaryRecord summaryRecord) {
        summaryRecord.setModifyId(SecurityUtils.getLoginUserAccount());
        summaryRecord.setModifyTime(LocalDateTime.now());
    }
}