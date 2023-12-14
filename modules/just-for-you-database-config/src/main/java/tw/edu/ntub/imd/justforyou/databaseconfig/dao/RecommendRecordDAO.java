package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommendRecordDAO extends BaseViewDAO<RecommendRecord, Integer> {
    List<RecommendRecord> findByUserId(@Param("userId") String userId);

    List<RecommendRecord> findByUserIdAndEmotionTag(String userId, Integer emotionTag);

    @Query("SELECT r.emotionTag FROM RecommendRecord r WHERE r.userId = :userId GROUP BY r.emotionTag")
    List<Integer> findEmotionTagByUserId(@Param("userId") String userId);

    List<RecommendRecord> findByUserIdAndSongContaining(String userId, String song);

    List<RecommendRecord> findByUserIdAndCollectionIsTrue(String userId);
}