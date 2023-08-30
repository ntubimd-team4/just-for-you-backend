package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicRecommendDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.service.MusicRecommendService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicRecommendTransformer;

@Service
public class MusicRecommendServiceImpl extends BaseServiceImpl<MusicRecommendBean, MusicRecommend, Integer> implements MusicRecommendService {
    private final MusicRecommendDAO musicRecommendDAO;
    private final MusicRecommendTransformer musicRecommendTransformer;

    public MusicRecommendServiceImpl(MusicRecommendDAO musicRecommendDAO, MusicRecommendTransformer musicRecommendTransformer) {
        super(musicRecommendDAO, musicRecommendTransformer);
        this.musicRecommendDAO = musicRecommendDAO;
        this.musicRecommendTransformer = musicRecommendTransformer;
    }

    @Override
    public MusicRecommendBean save(MusicRecommendBean musicRecommendBean) {
        return null;
    }
}