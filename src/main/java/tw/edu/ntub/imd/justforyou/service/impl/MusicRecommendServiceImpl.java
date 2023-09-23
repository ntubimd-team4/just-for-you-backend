package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicRecommendDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.MusicRecommendService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicRecommendTransformer;

import java.util.Optional;

@Service
public class MusicRecommendServiceImpl extends BaseServiceImpl<MusicRecommendBean, MusicRecommend, Integer> implements MusicRecommendService {
    private final MusicRecommendDAO musicRecommendDAO;
    private final MusicRecommendTransformer musicRecommendTransformer;

    public MusicRecommendServiceImpl(MusicRecommendDAO musicRecommendDAO,
                                     MusicRecommendTransformer musicRecommendTransformer) {
        super(musicRecommendDAO, musicRecommendTransformer);
        this.musicRecommendDAO = musicRecommendDAO;
        this.musicRecommendTransformer = musicRecommendTransformer;
    }

    @Override
    public MusicRecommendBean save(MusicRecommendBean musicRecommendBean) {
        return null;
    }

    @Override
    public void updateCollection(Integer rid) {
        Optional<MusicRecommend> musicRecommendOptional = musicRecommendDAO.findById(rid);
        if (musicRecommendOptional.isPresent()) {
            MusicRecommend musicRecommend = musicRecommendOptional.get();
            musicRecommend.setCollection(!musicRecommend.getCollection());
            musicRecommendDAO.update(musicRecommend);
        } else {
            throw new NotFoundException("查無此筆資料： " + rid);
        }
    }
}