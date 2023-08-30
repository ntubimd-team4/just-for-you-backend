package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.EmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicEmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicRecommendDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.service.transformer.EmotionTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmotionServiceImpl extends BaseServiceImpl<EmotionBean, Emotion, Integer> implements EmotionService {
    private final EmotionDAO emotionDAO;
    private final EmotionTransformer emotionTransformer;
    private final MusicDAO musicDAO;
    private final MusicEmotionDAO musicEmotionDAO;
    private final MusicRecommendDAO musicRecommendDAO;

    public EmotionServiceImpl(EmotionDAO emotionDAO,
                              EmotionTransformer emotionTransformer,
                              MusicDAO musicDAO,
                              MusicEmotionDAO musicEmotionDAO,
                              MusicRecommendDAO musicRecommendDAO) {
        super(emotionDAO, emotionTransformer);
        this.emotionDAO = emotionDAO;
        this.emotionTransformer = emotionTransformer;
        this.musicDAO = musicDAO;
        this.musicEmotionDAO = musicEmotionDAO;
        this.musicRecommendDAO = musicRecommendDAO;
    }

    @Override
    public EmotionBean save(EmotionBean emotionBean) {
        return null;
    }

    @Override
    public List<String> recommendMusic(Integer sid) {
        List<Integer> emotionList = emotionDAO.findBySid(sid);
        List<MusicEmotion> musicList = musicEmotionDAO.findByEmotionTagIn(emotionList);
        List<String> recommendMusicList = new ArrayList<>();
        for (MusicEmotion musicEmotion : musicList) {
            MusicRecommend musicRecommend = new MusicRecommend();
            musicRecommend.setSid(sid);
            musicRecommend.setMid(musicEmotion.getMid());

            Optional<Music> music = musicDAO.findByMid(musicEmotion.getMid());
            music.ifPresent(value -> recommendMusicList.add(value.getSong()));
            musicRecommendDAO.save(musicRecommend);
        }
        return recommendMusicList;
    }
}