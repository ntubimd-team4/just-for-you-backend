package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.ConsultationRecordDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.ConsultationRecord;
import tw.edu.ntub.imd.justforyou.service.ConsultationRecordService;
import tw.edu.ntub.imd.justforyou.service.transformer.ConsultationRecordTransformer;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationRecordServiceImpl extends BaseServiceImpl<ConsultationRecordBean, ConsultationRecord, Integer> implements ConsultationRecordService {
    private final ConsultationRecordDAO consultationRecordDAO;
    private final ConsultationRecordTransformer consultationRecordTransformer;

    public ConsultationRecordServiceImpl(ConsultationRecordDAO consultationRecordDAO,
                                         ConsultationRecordTransformer consultationRecordTransformer) {
        super(consultationRecordDAO, consultationRecordTransformer);
        this.consultationRecordDAO = consultationRecordDAO;
        this.consultationRecordTransformer = consultationRecordTransformer;
    }

    @Override
    public ConsultationRecordBean save(ConsultationRecordBean consultationRecordBean) {
        ConsultationRecord consultationRecord =
                consultationRecordDAO.save(consultationRecordTransformer.transferToEntity(consultationRecordBean));
        return consultationRecordTransformer.transferToBean(consultationRecord);
    }

    @Override
    public ConsultationRecordBean getBySid(Integer sid) {
        Optional<ConsultationRecord> consultationRecordOptional = consultationRecordDAO.findBySidOrderByCreateTimeDesc(sid);
        if (consultationRecordOptional.isPresent()) {
            ConsultationRecord consultationRecord = consultationRecordOptional.get();
            return consultationRecordTransformer.transferToBean(consultationRecord);
        } else {
            return new ConsultationRecordBean();
        }
    }
}