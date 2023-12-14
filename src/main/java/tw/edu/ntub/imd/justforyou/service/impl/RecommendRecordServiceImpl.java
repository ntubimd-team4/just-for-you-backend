package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.RecommendRecordDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.service.transformer.RecommendRecordTransformer;

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
    public List<Integer> searchUserEmotionTag(String userId) {
        return recommendRecordDAO.findEmotionTagByUserId(userId);
    }

    @Override
    public List<RecommendRecordBean> searchCollectionIsTrue(String userId) {
        return CollectionUtils.map(
                recommendRecordDAO.findByUserIdAndCollectionIsTrue(userId),
                recommendRecordTransformer::transferToBean);
    }

    @Override
    public List<RecommendRecordBean> searchByEstablishTime(String userId, String song) {
        return CollectionUtils.map(
                recommendRecordDAO.findByUserIdAndSongContainingGroupBySong(userId, song),
                recommendRecordTransformer::transferToBean);
    }

    @Override
    public List<RecommendRecordBean> searchByEstablishTime(String userId) {
        return CollectionUtils.map(
                recommendRecordDAO.findByUserIdGroupBySong(userId),
                recommendRecordTransformer::transferToBean);
    }

    @Override
    public List<RecommendRecordBean> searchByEstablishTime(String userId, Integer tag) {
        return CollectionUtils.map(
                recommendRecordDAO.findByUserIdAndEmotionTagGroupBySong(userId, tag),
                recommendRecordTransformer::transferToBean);
    }
}