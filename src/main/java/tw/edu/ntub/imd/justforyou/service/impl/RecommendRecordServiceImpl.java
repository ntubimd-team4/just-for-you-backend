package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.RecommendRecordDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.service.transformer.RecommendRecordTransformer;

@Service
public class RecommendRecordServiceImpl extends BaseViewServiceImpl<RecommendRecordBean, RecommendRecord, Integer> implements RecommendRecordService {
    private final RecommendRecordDAO recommendRecordDAO;
    private final RecommendRecordTransformer recommendRecordTransformer;

    public RecommendRecordServiceImpl(RecommendRecordDAO recommendRecordDAO, RecommendRecordTransformer recommendRecordTransformer) {
        super(recommendRecordDAO, recommendRecordTransformer);
        this.recommendRecordDAO = recommendRecordDAO;
        this.recommendRecordTransformer = recommendRecordTransformer;
    }
}