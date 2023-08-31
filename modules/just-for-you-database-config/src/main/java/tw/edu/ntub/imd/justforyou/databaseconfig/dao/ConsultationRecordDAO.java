package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.ConsultationRecord;

import java.util.List;

@Repository
public interface ConsultationRecordDAO extends BaseDAO<ConsultationRecord, Integer> {
    List<ConsultationRecord> findBySidOrderByCreateTimeDesc(Integer sid);
}