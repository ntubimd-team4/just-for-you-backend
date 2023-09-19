package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicRecommendDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.specification.UserAccountSpecification;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.MusicRecommendService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicRecommendTransformer;
import tw.edu.ntub.imd.justforyou.service.transformer.UserAccountTransformer;

import java.util.Optional;

@Service
public class MusicRecommendServiceImpl extends BaseServiceImpl<MusicRecommendBean, MusicRecommend, Integer> implements MusicRecommendService {
    private final MusicRecommendDAO musicRecommendDAO;
    private final UserAccountTransformer transformer;
    private final UserAccountSpecification specification;
    private final MusicRecommendTransformer musicRecommendTransformer;

    public MusicRecommendServiceImpl(MusicRecommendDAO musicRecommendDAO, MusicRecommendTransformer musicRecommendTransformer, UserAccountTransformer transformer, UserAccountSpecification specification) {
        super(musicRecommendDAO, musicRecommendTransformer);
        this.musicRecommendDAO = musicRecommendDAO;
        this.transformer = transformer;
        this.specification = specification;
        this.musicRecommendTransformer = musicRecommendTransformer;
    }

    @Override
    public MusicRecommendBean save(MusicRecommendBean musicRecommendBean) {
        return null;
    }

    @Override
    public void updateCollection(Integer id) {
        Optional<MusicRecommend> musicRecommendOptional = musicRecommendDAO.findById(id);
        if (musicRecommendOptional.isPresent()) {
            MusicRecommend musicRecommend = musicRecommendOptional.get();
            musicRecommend.setCollection(!musicRecommend.getCollection());
            musicRecommendDAO.update(musicRecommend);
        } else {
            throw new NotFoundException("查無此筆資料： " + id);
        }
    }
}