package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;

import java.util.List;

public interface ConsultationRecordService extends BaseService<ConsultationRecordBean, Integer> {
    List<ConsultationRecordBean> searchBySid(Integer sid);
}