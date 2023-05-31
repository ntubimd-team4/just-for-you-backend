package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.EmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.service.transformer.EmotionTransformer;

@Service
public class EmotionServiceImpl extends BaseServiceImpl<EmotionBean, Emotion, Integer> implements EmotionService {
    private final EmotionDAO emotionDAO;
    private final EmotionTransformer emotionTransformer;

    public EmotionServiceImpl(EmotionDAO emotionDAO, EmotionTransformer emotionTransformer) {
        super(emotionDAO, emotionTransformer);
        this.emotionDAO = emotionDAO;
        this.emotionTransformer = emotionTransformer;
    }

    @Override
    public EmotionBean save(EmotionBean emotionBean) {
        return null;
    }
}