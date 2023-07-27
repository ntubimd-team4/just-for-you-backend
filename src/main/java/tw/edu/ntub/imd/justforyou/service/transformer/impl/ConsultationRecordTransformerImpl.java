package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.ConsultationRecord;
import tw.edu.ntub.imd.justforyou.service.transformer.ConsultationRecordTransformer;

import javax.annotation.Nonnull;

@Component
public class ConsultationRecordTransformerImpl implements ConsultationRecordTransformer {
    @Nonnull
    @Override
    public ConsultationRecord transferToEntity(@Nonnull ConsultationRecordBean consultationRecordTransformer) {
        return JavaBeanUtils.copy(consultationRecordTransformer, new ConsultationRecord());
    }

    @Nonnull
    @Override
    public ConsultationRecordBean transferToBean(@Nonnull ConsultationRecord consultationRecord) {
        return JavaBeanUtils.copy(consultationRecord, new ConsultationRecordBean());
    }
}