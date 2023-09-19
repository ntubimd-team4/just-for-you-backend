package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;

import java.util.List;

public interface RecommendRecordService extends BaseViewService<RecommendRecordBean, Integer> {
    List<RecommendRecordBean> searchByUserId(String userId);
}