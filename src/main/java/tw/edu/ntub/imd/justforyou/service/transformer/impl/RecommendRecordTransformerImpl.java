package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.service.transformer.RecommendRecordTransformer;

import javax.annotation.Nonnull;

@Component
public class RecommendRecordTransformerImpl implements RecommendRecordTransformer {
    @Nonnull
    @Override
    public RecommendRecord transferToEntity(@Nonnull RecommendRecordBean recommendRecordBean) {
        return JavaBeanUtils.copy(recommendRecordBean, new RecommendRecord());
    }

    @Nonnull
    @Override
    public RecommendRecordBean transferToBean(@Nonnull RecommendRecord recommendRecord) {
        return JavaBeanUtils.copy(recommendRecord, new RecommendRecordBean());
    }
}