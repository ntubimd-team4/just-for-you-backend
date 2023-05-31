package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.service.transformer.EmotionTransformer;

@Component
public class EmotionTransformerImpl implements EmotionTransformer {
    @NonNull
    @Override
    public Emotion transferToEntity(@NonNull EmotionBean emotionBean) {
        return JavaBeanUtils.copy(emotionBean, new Emotion());
    }

    @NonNull
    @Override
    public EmotionBean transferToBean(@NonNull Emotion emotion) {
        return JavaBeanUtils.copy(emotion, new EmotionBean());
    }
}