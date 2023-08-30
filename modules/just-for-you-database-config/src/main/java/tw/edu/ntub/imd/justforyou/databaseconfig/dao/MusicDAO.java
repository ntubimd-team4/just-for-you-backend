package tw.edu.ntub.imd.justforyou.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;

import java.util.Optional;

@Repository
public interface MusicDAO extends BaseDAO<Music, Integer> {
    Optional<Music> findByMid(Integer mid);
}