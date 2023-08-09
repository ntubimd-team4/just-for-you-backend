package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.EmotionBean;

import java.util.List;

public interface EmotionService extends BaseService<EmotionBean, Integer> {
    List<String> recommendMusic(Integer sid);
}