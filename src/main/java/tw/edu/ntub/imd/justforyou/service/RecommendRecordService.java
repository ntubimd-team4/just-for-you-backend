package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;

import java.time.LocalDateTime;
import java.util.List;

public interface RecommendRecordService extends BaseViewService<RecommendRecordBean, Integer> {
    List<RecommendRecordBean> searchByEstablishTime(String userId);

    List<RecommendRecordBean> searchByEstablishTime(String userId, Integer tag);

    List<Integer> searchUserEmotionTag(String loginUserAccount);

    List<RecommendRecordBean> searchByEstablishTime(String userId, String song);
}