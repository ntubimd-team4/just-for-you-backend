package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;

import java.util.List;

@Repository
public interface EmotionDAO extends BaseDAO<Emotion, Integer> {
    @Query("SELECT e.emotionTag FROM Emotion e WHERE e.sid = :sid")
    List<Integer> findBySid(@Param("sid") Integer sid);
}