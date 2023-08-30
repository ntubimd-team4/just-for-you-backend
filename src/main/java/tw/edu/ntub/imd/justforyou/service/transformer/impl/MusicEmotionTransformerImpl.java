package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.MusicEmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicEmotionTransformer;

import javax.annotation.Nonnull;

@Component
public class MusicEmotionTransformerImpl implements MusicEmotionTransformer {
    @Nonnull
    @Override
    public MusicEmotion transferToEntity(@Nonnull MusicEmotionBean musicEmotionBean) {
        return JavaBeanUtils.copy(musicEmotionBean, new MusicEmotion());
    }

    @Nonnull
    @Override
    public MusicEmotionBean transferToBean(@Nonnull MusicEmotion musicEmotion) {
        return JavaBeanUtils.copy(musicEmotion, new MusicEmotionBean());
    }
}