package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;

@Repository
public interface EmotionDAO extends BaseDAO<Emotion, Integer> {
}