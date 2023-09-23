package tw.edu.ntub.imd.justforyou.service;

import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;

import java.time.LocalDateTime;
import java.util.List;

public interface RecommendRecordService extends BaseViewService<RecommendRecordBean, Integer> {
    List<LocalDateTime> searchByUserId(String userId);

    List<RecommendRecordBean> searchByEstablishTime(String userId, LocalDateTime establishTime);

    List<LocalDateTime> searchByUserIdAndEmotionTag(String userId, Integer tag);

    List<RecommendRecordBean> searchByEstablishTime(String userId, LocalDateTime establishTime, Integer tag);
}