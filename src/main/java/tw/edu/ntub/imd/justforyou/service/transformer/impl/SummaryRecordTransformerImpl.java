package tw.edu.ntub.imd.justforyou.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;
import tw.edu.ntub.imd.justforyou.service.transformer.SummaryRecordTransformer;

@Component
public class SummaryRecordTransformerImpl implements SummaryRecordTransformer {
    @NonNull
    @Override
    public SummaryRecord transferToEntity(@NonNull SummaryRecordBean summaryRecordBean) {
        return JavaBeanUtils.copy(summaryRecordBean, new SummaryRecord());
    }

    @NonNull
    @Override
    public SummaryRecordBean transferToBean(@NonNull SummaryRecord summaryRecord) {
        return JavaBeanUtils.copy(summaryRecord, new SummaryRecordBean());
    }
}