package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.TopicBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.justforyou.service.transformer.TopicTransformer;

@Component
public class TopicTransformerImpl implements TopicTransformer {
    @NonNull
    @Override
    public Topic transferToEntity(@NonNull TopicBean topicBean) {
        return JavaBeanUtils.copy(topicBean, new Topic());
    }

    @NonNull
    @Override
    public TopicBean transferToBean(@NonNull Topic topic) {
        return JavaBeanUtils.copy(topic, new TopicBean());
    }
}