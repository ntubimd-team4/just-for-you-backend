package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Music> recommendMusic(Integer sid) {
        List<Integer> emotionList = emotionDAO.findBySid(sid);
        List<MusicEmotion> musicList = musicEmotionDAO.findByEmotionTagIn(emotionList);
        List<Music> recommendMusicList = new ArrayList<>();
        for (MusicEmotion musicEmotion : musicList) {
            MusicRecommend musicRecommend = new MusicRecommend();
            musicRecommend.setSid(sid);
            musicRecommend.setMid(musicEmotion.getMid());
            musicRecommendDAO.save(musicRecommend);

            Music music = new Music();
            Optional<Music> musicOptional = musicDAO.findByMid(musicEmotion.getMid());
            if (musicOptional.isPresent()) {
                Music musicData = musicOptional.get();
                JavaBeanUtils.copy(musicData, music);
            }
            recommendMusicList.add(music);
        }
        Collections.shuffle(recommendMusicList);
        return recommendMusicList.size() > 5 ?
                recommendMusicList.stream().distinct().collect(Collectors.toList()).subList(0, 5) :
                recommendMusicList;
    }
}