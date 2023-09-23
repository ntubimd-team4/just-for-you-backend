package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommendRecordDAO extends BaseViewDAO<RecommendRecord, Integer> {
    @Query("SELECT DISTINCT r.establishTime FROM RecommendRecord r WHERE r.userId = :userId")
    List<LocalDateTime> findByUserId(@Param("userId") String userId);

    List<RecommendRecord> findByUserIdAndEstablishTime(String userId, LocalDateTime establishTime);

    @Query("SELECT DISTINCT r.establishTime FROM RecommendRecord r WHERE r.userId = :userId AND r.emotionTag = :emotionTag")
    List<LocalDateTime> findByTag(@Param("userId") String userId, @Param("emotionTag") Integer emotionTag);

    List<RecommendRecord> findByUserIdAndEstablishTimeAndEmotionTag(String userId, LocalDateTime establishTime, Integer emotionTag);
}