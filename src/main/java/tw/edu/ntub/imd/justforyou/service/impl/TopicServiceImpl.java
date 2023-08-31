package tw.edu.ntub.imd.justforyou.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.justforyou.bean.TopicBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.TopicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.TopicCode;
import tw.edu.ntub.imd.justforyou.service.TopicService;
import tw.edu.ntub.imd.justforyou.service.transformer.TopicTransformer;
import tw.edu.ntub.imd.justforyou.util.data.SymbolUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl extends BaseServiceImpl<TopicBean, Topic, Integer> implements TopicService {
    private final TopicDAO topicDAO;
    private final TopicTransformer topicTransformer;

    public TopicServiceImpl(TopicDAO topicDAO, TopicTransformer topicTransformer) {
        super(topicDAO, topicTransformer);
        this.topicDAO = topicDAO;
        this.topicTransformer = topicTransformer;
    }

    @Override
    public TopicBean save(TopicBean topicBean) {
        return null;
    }

    @Override
    public String searchBySid(Integer sid) {
        List<Topic> topicList = topicDAO.findBySid(sid);
        List<String> stringList = new ArrayList<>();
        for (Topic topic : topicList){
            stringList.add(TopicCode.of(topic.getTopic()));
        }
        return SymbolUtils.remoteSymbol(stringList);
    }
}