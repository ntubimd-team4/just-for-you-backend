package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendRecordDAO extends BaseViewDAO<RecommendRecord, Integer> {
    @Query("FROM RecommendRecord r WHERE r.userId = :userId GROUP BY r.userId, r.song")
    List<RecommendRecord> findByUserIdGroupBySong(@Param("userId") String userId);

    @Query("FROM RecommendRecord r WHERE r.userId = :userId AND r.emotionTag = :emotionTag GROUP BY r.userId, r.song")
    List<RecommendRecord> findByUserIdAndEmotionTagGroupBySong(@Param("userId") String userId,
                                                               @Param("emotionTag") Integer emotionTag);

    @Query("SELECT r.emotionTag FROM RecommendRecord r WHERE r.userId = :userId GROUP BY r.emotionTag")
    List<Integer> findEmotionTagByUserId(@Param("userId") String userId);

    @Query("FROM RecommendRecord r WHERE r.userId = :userId AND r.song like %:song% GROUP BY r.userId, r.song")
    List<RecommendRecord> findByUserIdAndSongContainingGroupBySong(@Param("userId") String userId,
                                                                   @Param("song") String song);

    List<RecommendRecord> findByUserIdAndCollectionIsTrue(String userId);

    @Query("FROM RecommendRecord r WHERE r.mid = :mid GROUP BY r.song")
    Optional<RecommendRecord> findByMid(@Param("mid")Integer mid);
}