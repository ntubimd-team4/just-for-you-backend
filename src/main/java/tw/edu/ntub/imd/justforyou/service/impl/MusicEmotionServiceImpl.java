package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.MusicEmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicEmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.service.MusicEmotionService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicEmotionTransformer;

@Service
public class MusicEmotionServiceImpl extends BaseServiceImpl<MusicEmotionBean, MusicEmotion, Integer> implements MusicEmotionService {
    private final MusicEmotionDAO musicEmotionDAO;
    private final MusicEmotionTransformer musicEmotionTransformer;

    public MusicEmotionServiceImpl(MusicEmotionDAO musicEmotionDAO, MusicEmotionTransformer musicEmotionTransformer) {
        super(musicEmotionDAO, musicEmotionTransformer);
        this.musicEmotionDAO = musicEmotionDAO;
        this.musicEmotionTransformer = musicEmotionTransformer;
    }

    @Override
    public MusicEmotionBean save(MusicEmotionBean musicEmotionBean) {
        return null;
    }
}