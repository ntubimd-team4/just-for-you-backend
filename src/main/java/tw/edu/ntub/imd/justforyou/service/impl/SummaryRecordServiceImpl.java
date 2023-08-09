package tw.edu.ntub.imd.justforyou.service.impl;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.EmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.SummaryRecordDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.TopicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.TopicCode;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.SummaryRecordService;
import tw.edu.ntub.imd.justforyou.service.transformer.SummaryRecordTransformer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryRecordServiceImpl extends BaseServiceImpl<SummaryRecordBean, SummaryRecord, Integer> implements SummaryRecordService {
    @Value("${server.summary-token.value}")
    private String summaryToken;
    @Value("${server.emotion-token.value}")
    private String emotionToken;
    @Value("${server.topic-token.value}")
    private String topicToken;
    private final SummaryRecordDAO summaryRecordDAO;
    private final SummaryRecordTransformer summaryRecordTransformer;
    private final EmotionDAO emotionDAO;
    private final TopicDAO topicDAO;

    public SummaryRecordServiceImpl(SummaryRecordDAO summaryRecordDAO,
                                    SummaryRecordTransformer summaryRecordTransformer,
                                    EmotionDAO emotionDAO,
                                    TopicDAO topicDAO) {
        super(summaryRecordDAO, summaryRecordTransformer);
        this.summaryRecordDAO = summaryRecordDAO;
        this.summaryRecordTransformer = summaryRecordTransformer;
        this.emotionDAO = emotionDAO;
        this.topicDAO = topicDAO;
    }

    @Override
    public SummaryRecordBean save(SummaryRecordBean summaryRecordBean) {
        return null;
    }

    @Override
    public Integer saveSummaryRecord(SummaryRecordBean summaryRecordBean) {
        String id = SecurityUtils.getLoginUserAccount();

        OpenAiService service = new OpenAiService(summaryToken, Duration.ofSeconds(60));
        String summaryRecordText;

        try {
            summaryRecordText = service.createCompletion(summaryRecordRequest(summaryRecordBean.getPrompt())).getChoices().get(0).getText();
        } catch (Exception e) {
            throw new NotFoundException("請重新發送請求");
        }
        SummaryRecord summaryRecord = summaryRecordTransformer.transferToEntity(summaryRecordBean);
        summaryRecord.setUserId(id);
        summaryRecord.setContent(summaryRecordBean.getPrompt());
        summaryRecord.setSummary(summaryRecordText.replace("\n", ""));
        summaryRecord.setUserId(SecurityUtils.getLoginUserAccount());
        summaryRecordDAO.save(summaryRecord);

        return summaryRecord.getSid();
    }

    private CompletionRequest summaryRecordRequest(String prompt) {
        return CompletionRequest.builder().model("text-davinci-003").prompt(prompt + "請針對這句話以第一人稱進行簡單摘要").temperature(0.5).maxTokens(2048).topP(1D).frequencyPenalty(0D).presencePenalty(0D).build();
    }

    @Override
    public List<String> saveEmotion(Integer sid, String prompt) {
        OpenAiService service = new OpenAiService(emotionToken, Duration.ofSeconds(60));
        String emotionText;

        try {
            emotionText = service.createCompletion(emotionRequest(prompt)).getChoices().get(0).getText();
        } catch (Exception e) {
            throw new NotFoundException("請重新發送請求");
        }

        String[] emotions = {"平靜", "快樂", "狂喜", "友愛", "接受", "信任", "平靜", "屈服", "擔心", "恐懼", "驚悚", "敬畏", "不解", "驚訝", "驚愕", "反對", "傷感", "悲傷", "悲痛", "懊悔", "厭倦", "厭惡", "憎恨", "鄙夷", "不耐煩", "生氣", "暴怒", "挑釁", "關心", "期待", "警惕", "樂觀"};
        List<String> emotionList = new ArrayList<>();
        for (String emotion : emotions) {
            if (emotionText.contains(emotion)) {
                emotionList.add(emotion);
            }
        }

        for (String emotionStr : emotionList) {
            Emotion emotion = new Emotion();
            emotion.setSid(sid);
            emotion.setEmotionTag(EmotionCode.of(emotionStr));
            emotionDAO.save(emotion);
        }
        return emotionList;
    }

    private CompletionRequest emotionRequest(String prompt) {
        String mood = "平靜、快樂、狂喜、友愛、接受、信任、平靜、屈服、擔心、恐懼、驚悚、敬畏、不解、驚訝、驚愕、反對、傷感、悲傷、悲痛、懊悔、厭倦、厭惡、憎恨、鄙夷、不耐煩、生氣、暴怒、挑釁、關心、期待、警惕、樂觀";
        return CompletionRequest.builder().model("text-davinci-003").prompt(prompt + "請從以下" + mood + "這幾個情緒中判斷出這句話帶有哪些情緒").temperature(0.5).maxTokens(2048).topP(1D).frequencyPenalty(0D).presencePenalty(0D).build();
    }


    @Override
    public void saveTopic(Integer sid, String prompt) {
        OpenAiService service = new OpenAiService(topicToken, Duration.ofSeconds(60));
        String topicText;

        try {
            topicText = service.createCompletion(topicRequest(prompt)).getChoices().get(0).getText();
        } catch (Exception e) {
            throw new NotFoundException("請重新發送請求");
        }

        String[] topics = {"自我探索", "情感困擾", "家庭關係", "心理疾患或傾向", "情緒管理", "人際關係", "學業學習", "生涯探索與規劃", "生活適應", "網路沉迷", "生理健康", "心理測驗"};
        List<String> topicList = new ArrayList<>();
        for (String topic : topics) {
            if (topicText.contains(topic)) {
                topicList.add(topic);
            }
        }

        for (String topicStr : topicList) {
            Topic topic = new Topic();
            topic.setSid(sid);
            topic.setTopic(TopicCode.of(topicStr));
            topicDAO.save(topic);
        }
    }

    private CompletionRequest topicRequest(String prompt) {
        String topic = "「自我探索」、「情感困擾」、「家庭關係」、「學業學習」、「生理健康」、「心理測驗」、「生涯探索與規劃」、「心理疾患或傾向」、「情緒管理」、「人際關係」、「生活適應」、「網路沉迷」";
        return CompletionRequest.builder().model("text-davinci-003").prompt(prompt + "請從以下" + topic + "這幾個分類判斷出這句話屬於哪幾個分類").temperature(0.5).maxTokens(2048).topP(1D).frequencyPenalty(0D).presencePenalty(0D).build();
    }
}