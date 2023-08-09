package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicRecommendTransformer;

import javax.annotation.Nonnull;

@Component
public class MusicRecommendTransformerImpl implements MusicRecommendTransformer {
    @Nonnull
    @Override
    public MusicRecommend transferToEntity(@Nonnull MusicRecommendBean musicRecommendBean) {
        return JavaBeanUtils.copy(musicRecommendBean, new MusicRecommend());
    }

    @Nonnull
    @Override
    public MusicRecommendBean transferToBean(@Nonnull MusicRecommend musicRecommend) {
        return JavaBeanUtils.copy(musicRecommend, new MusicRecommendBean());
    }
}