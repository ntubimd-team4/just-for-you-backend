package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;

import java.util.List;

@Repository
public interface SummaryRecordDAO extends BaseDAO<SummaryRecord, Integer> {
    List<SummaryRecord> findByUserIdOrderByEstablishTimeDesc(String userId);
}