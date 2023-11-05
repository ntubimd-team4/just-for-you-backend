package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;

import java.util.List;

@Repository
public interface SummaryRecordDAO extends BaseDAO<SummaryRecord, Integer> {
    List<SummaryRecord> findByUserIdAndTeacherOrderByEstablishTimeDesc(String userId, String teacher);

    @Query("SELECT s.userId FROM SummaryRecord s WHERE s.teacher = :id GROUP BY s.userId")
    List<String> findByTeacher(@Param("id") String id);
}