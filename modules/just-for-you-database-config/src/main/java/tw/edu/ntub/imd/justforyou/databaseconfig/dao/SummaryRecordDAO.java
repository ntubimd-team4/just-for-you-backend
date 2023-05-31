package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;

@Repository
public interface SummaryRecordDAO extends BaseDAO<SummaryRecord, Integer> {
    Page<SummaryRecord> findByUserIdOrderBySidDesc(String id, Pageable pageable);
}