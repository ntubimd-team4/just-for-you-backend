package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Topic;

import java.util.List;

@Repository
public interface TopicDAO extends BaseDAO<Topic, Integer> {
    List<Topic> findBySid(Integer sid);
}