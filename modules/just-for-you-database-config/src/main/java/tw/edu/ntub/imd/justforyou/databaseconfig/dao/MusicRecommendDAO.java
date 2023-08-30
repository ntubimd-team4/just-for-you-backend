package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;

@Repository
public interface MusicRecommendDAO extends BaseDAO<MusicRecommend, Integer> {
}