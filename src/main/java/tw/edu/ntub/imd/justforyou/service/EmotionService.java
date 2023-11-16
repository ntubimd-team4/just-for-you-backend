package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;

import java.util.List;

public interface EmotionService extends BaseService<EmotionBean, Integer> {
    List<Music> recommendMusic(Integer sid, List<MusicEmotion> musicEmotionList);

    List<MusicEmotion> searchMusic(Integer sid);

    String generateText(List<MusicEmotion> musicEmotionList);

    String searchBySid(Integer sid);
}