package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;

import java.util.List;

public interface SummaryRecordService extends BaseService<SummaryRecordBean, Integer> {
    String[] saveSummaryRecord(SummaryRecordBean summaryRecordBean);

    List<String> saveEmotion(Integer sid, String prompt);

    void saveTopic(Integer sid, String prompt);

    List<SummaryRecordBean> searchSummaryRecordList(String userId);
}