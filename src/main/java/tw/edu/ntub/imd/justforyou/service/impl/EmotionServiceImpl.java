package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.EmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicRecommendDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.service.transformer.EmotionTransformer;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmotionServiceImpl extends BaseServiceImpl<EmotionBean, Emotion, Integer> implements EmotionService {
    private final EmotionDAO emotionDAO;
    private final EmotionTransformer emotionTransformer;
    private final MusicDAO musicDAO;
    private final MusicRecommendDAO musicRecommendDAO;

    public EmotionServiceImpl(EmotionDAO emotionDAO, EmotionTransformer emotionTransformer, MusicDAO musicDAO, MusicRecommendDAO musicRecommendDAO) {
        super(emotionDAO, emotionTransformer);
        this.emotionDAO = emotionDAO;
        this.emotionTransformer = emotionTransformer;
        this.musicDAO = musicDAO;
        this.musicRecommendDAO = musicRecommendDAO;
    }

    @Override
    public EmotionBean save(EmotionBean emotionBean) {
        return null;
    }

    @Override
    public List<String> recommendMusic(Integer sid) {
        List<Integer> emotionList = emotionDAO.findBySid(sid);
        List<Music> musicList = musicDAO.findByEmotionTagIn(emotionList);
        List<String> recommendMusicList = new ArrayList<>();
        for (Music music : musicList) {
            MusicRecommend musicRecommend = new MusicRecommend();
            musicRecommend.setSid(sid);
            musicRecommend.setMid(music.getMid());
            recommendMusicList.add(music.getSong());
            musicRecommendDAO.save(musicRecommend);
        }
        return recommendMusicList;
    }
}