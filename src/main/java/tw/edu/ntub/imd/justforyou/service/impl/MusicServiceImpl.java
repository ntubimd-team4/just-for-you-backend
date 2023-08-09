package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.MusicBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.service.MusicService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicTransformer;

@Service
public class MusicServiceImpl extends BaseServiceImpl<MusicBean, Music, Integer> implements MusicService {
    private final MusicDAO musicDAO;
    private final MusicTransformer musicTransformer;

    public MusicServiceImpl(MusicDAO musicDAO, MusicTransformer musicTransformer) {
        super(musicDAO, musicTransformer);
        this.musicDAO = musicDAO;
        this.musicTransformer = musicTransformer;
    }

    @Override
    public MusicBean save(MusicBean musicBean) {
        return null;
    }
}