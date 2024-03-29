package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;

import java.util.Optional;

public interface ConsultationRecordService extends BaseService<ConsultationRecordBean, Integer> {
    ConsultationRecordBean getBySid(Integer sid);
}