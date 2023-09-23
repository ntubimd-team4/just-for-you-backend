package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.RecommendRecordDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.service.transformer.RecommendRecordTransformer;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendRecordServiceImpl extends BaseViewServiceImpl<RecommendRecordBean, RecommendRecord, Integer> implements RecommendRecordService {
    private final RecommendRecordDAO recommendRecordDAO;
    private final RecommendRecordTransformer recommendRecordTransformer;

    public RecommendRecordServiceImpl(RecommendRecordDAO recommendRecordDAO, RecommendRecordTransformer recommendRecordTransformer) {
        super(recommendRecordDAO, recommendRecordTransformer);
        this.recommendRecordDAO = recommendRecordDAO;
        this.recommendRecordTransformer = recommendRecordTransformer;
    }

    @Override
    public List<LocalDateTime> searchByUserId(String userId) {
        return recommendRecordDAO.findByUserId(userId);
    }

    @Override
    public List<LocalDateTime> searchByUserIdAndEmotionTag(String userId, Integer tag) {
        return recommendRecordDAO.findByTag(userId, tag);
    }

    @Override
    public List<RecommendRecordBean> searchByEstablishTime(String userId, LocalDateTime establishTime) {
        return CollectionUtils.map(
                recommendRecordDAO.findByUserIdAndEstablishTime(userId, establishTime),
                recommendRecordTransformer::transferToBean);
    }

    @Override
    public List<RecommendRecordBean> searchByEstablishTime(String userId, LocalDateTime establishTime, Integer tag) {
        return CollectionUtils.map(
                recommendRecordDAO.findByUserIdAndEstablishTimeAndEmotionTag(userId, establishTime, tag),
                recommendRecordTransformer::transferToBean);
    }
}