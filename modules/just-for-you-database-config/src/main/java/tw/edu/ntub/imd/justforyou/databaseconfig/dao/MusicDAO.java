package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;

import java.util.List;

@Repository
public interface MusicDAO extends BaseDAO<Music, Integer> {
    List<Music> findByEmotionTagIn(List<Integer> emotionList);
}