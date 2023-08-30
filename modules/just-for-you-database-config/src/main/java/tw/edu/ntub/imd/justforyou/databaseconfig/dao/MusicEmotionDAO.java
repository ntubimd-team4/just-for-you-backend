package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;

import java.util.List;

@Repository
public interface MusicEmotionDAO extends BaseDAO<MusicEmotion, Integer> {
    List<MusicEmotion> findByEmotionTagIn(List<Integer> emotionList);
}