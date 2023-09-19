package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;

@Repository
public interface RecommendRecordDAO extends BaseViewDAO<RecommendRecord, Integer> {
}