package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.MusicBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicTransformer;

import javax.annotation.Nonnull;

@Component
public class MusicTransformerImpl implements MusicTransformer {
    @Nonnull
    @Override
    public Music transferToEntity(@Nonnull MusicBean musicBean) {
        return JavaBeanUtils.copy(musicBean, new Music());
    }

    @Nonnull
    @Override
    public MusicBean transferToBean(@Nonnull Music music) {
        return JavaBeanUtils.copy(music, new MusicBean());
    }
}