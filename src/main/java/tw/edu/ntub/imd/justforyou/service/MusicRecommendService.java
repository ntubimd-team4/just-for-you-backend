package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;

public interface MusicRecommendService extends BaseService<MusicRecommendBean, Integer> {
    String updateCollection(Integer rid);
}