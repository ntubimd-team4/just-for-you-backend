package tw.edu.ntub.imd.justforyou.service.impl;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.EmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.SummaryRecordDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.TopicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.UserAccountDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Level;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.TopicCode;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.SummaryRecordService;
import tw.edu.ntub.imd.justforyou.service.transformer.SummaryRecordTransformer;
import tw.edu.ntub.imd.justforyou.util.encryption.EncryptionUtils;

import javax.mail.MessagingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SummaryRecordServiceImpl extends BaseServiceImpl<SummaryRecordBean, SummaryRecord, Integer> implements SummaryRecordService {
    @Value("${server.summary-token.value}")
    private String summaryToken;
    @Value("${server.emotion-token.value}")
    private String emotionToken;
    @Value("${server.topic-token.value}")
    private String topicToken;
    @Value("${server.level-token.value}")
    private String levelToken;
    private final SummaryRecordDAO summaryRecordDAO;
    private final SummaryRecordTransformer summaryRecordTransformer;
    private final EmotionDAO emotionDAO;
    private final UserAccountDAO userAccountDAO;
    private final TopicDAO topicDAO;
    private final JavaMailSender mailSender;

    public SummaryRecordServiceImpl(SummaryRecordDAO summaryRecordDAO,
                                    SummaryRecordTransformer summaryRecordTransformer,
                                    EmotionDAO emotionDAO,
                                    UserAccountDAO userAccountDAO,
                                    TopicDAO topicDAO,
                                    JavaMailSender mailSender) {
        super(summaryRecordDAO, summaryRecordTransformer);
        this.summaryRecordDAO = summaryRecordDAO;
        this.summaryRecordTransformer = summaryRecordTransformer;
        this.emotionDAO = emotionDAO;
        this.userAccountDAO = userAccountDAO;
        this.topicDAO = topicDAO;
        this.mailSender = mailSender;
    }

    @Override
    public SummaryRecordBean save(SummaryRecordBean summaryRecordBean) {
        return null;
    }

    @Override
    public void update(Integer id, SummaryRecordBean summaryRecordBean) {
        Optional<SummaryRecord> optional = summaryRecordDAO.findById(id);
        if (optional.isPresent()) {
            SummaryRecord summaryRecord = optional.get();
            JavaBeanUtils.copy(summaryRecordBean, summaryRecord);
            if (summaryRecord.getLevel() == 3) {
                sendMail(EncryptionUtils.decryptText(summaryRecord.getContent()),
                        Level.LEVEL_THREE_TO_TEACHER.getLevel(),
                        summaryRecordBean.getTeacher());
            }
            summaryRecordDAO.update(summaryRecord);
        } else {
            throw new NotFoundException("找不到資料, id = " + id);
        }
    }

    @Override
    public String saveSummaryRecord(SummaryRecordBean summaryRecordBean) {
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
        summaryRecord.setContent(EncryptionUtils.cryptText(summaryRecordBean.getPrompt()));
        summaryRecord.setSummary(EncryptionUtils.cryptText(summaryRecordText.replace("\n", "")));
        summaryRecord.setUserId(SecurityUtils.getLoginUserAccount());
        summaryRecord.setLevel(getLevel(summaryRecordBean.getPrompt()));
        summaryRecordDAO.save(summaryRecord);

        return summaryRecord.getSid() + "," + summaryRecord.getLevel();
    }

    private CompletionRequest summaryRecordRequest(String prompt) {
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt + "請針對這句話以第一人稱進行簡單摘要")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
    }

    private Integer getLevel(String prompt) {
        OpenAiService levelService = new OpenAiService(levelToken, Duration.ofSeconds(60));
        String level;

        try {
            level = levelService.createCompletion(levelRequest(prompt)).getChoices().get(0).getText();
        } catch (Exception e) {
            throw new NotFoundException("請重新發送請求");
        }

        if (level.contains("一級")) {
            return 1;
        } else if (level.contains("二級")) {
            return 2;
        } else if (level.contains("三級")) {
            sendMail(prompt, Level.LEVEL_THREE.getLevel(), null);
            return 3;
        } else if (level.contains("四級")) {
            sendMail(prompt, Level.LEVEL_FOUR.getLevel(), null);
            return 4;
        } else {
            return 0;
        }
    }

    private CompletionRequest levelRequest(String prompt) {
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("一級:壓力可能在一般範圍，或輕度困擾\n" +
                        "二級:可能有中度情緒困擾\n" +
                        "三級:可能有嚴重情緒困擾\n" +
                        "四級:有時有自殺意念或有強烈之自殺行動、自殺計畫、偏激或立即有危險需立即通報\n" +
                        "請依照以上標準判斷出「" + prompt + "」這句話屬於哪一級別?")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
    }

    private void sendMail(String prompt, Integer level, String teacher) {
        String[] userAccounts;
        String levelName, appellation;
        switch (level) {
            case 3:
                levelName = Level.LEVEL_THREE.getLevelChar();
                appellation = Role.CASE_MANAGEMENT.getTypeName();
                userAccounts = userAccountDAO.findByCaseManagement();
                break;
            case 5:
                levelName = Level.LEVEL_THREE.getLevelChar();
                appellation = Role.TEACHER.getTypeName();
                userAccounts = new String[]{teacher};
                break;
            default:
                levelName = Level.LEVEL_FOUR.getLevelChar();
                appellation = Role.CASE_MANAGEMENT.getTypeName() + " / " + Role.TEACHER.getTypeName();
                userAccounts = userAccountDAO.findByAllTeacher();
        }
        sendMailContent(prompt, userAccounts, levelName, appellation);
    }

    private void sendMailContent(String prompt, String[] userAccounts, String levelName, String appellation) {
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom("諮屬於你 <ntubimd112404@gmail.com>");
            mimeMessageHelper.setBcc(userAccounts);
            mimeMessageHelper.setSubject("緊急！有" + levelName + "級通知！");
            mimeMessageHelper.setText("<html><head></head><body>" +
                    "<p>" + appellation + "您好：</p>" +
                    "<p>目前有同學輸入的心情小語被判定為<font color=\"#FF0000\"><b style=\"font-size: 20px;\">" + levelName +
                    "級狀態</b></font>，" +
                    "該同學所輸入的心情為：</p></br>" +
                    "<p style=\"font-size: 18px;\"><b>" + prompt + "</b></p></br>" +
                    "<p>請立即至系統查看並確認。</p>" +
                    "<a href=\"https://justforyou.ntub.edu.tw\">點我前往系統</a>" +
                    "</body></html>", true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new UnknownException(e);
        }
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

        String[] emotions = {"平靜", "快樂", "狂喜", "友愛", "接受", "信任", "崇敬", "屈服", "擔心", "恐懼", "驚悚", "敬畏", "不解", "驚訝", "驚愕", "反對", "傷感", "悲傷", "悲痛", "懊悔", "厭倦", "厭惡", "憎恨", "鄙夷", "不耐煩", "生氣", "暴怒", "挑釁", "關心", "期待", "警惕", "樂觀"};
        List<String> emotionList = new ArrayList<>();
        for (String emotion : emotions) {
            if (emotionText.contains(emotion)) {
                emotionList.add(emotion);
            }
        }

        for (String emotionStr : emotionList) {
            Emotion emotion = new Emotion();
            emotion.setSid(sid);
            emotion.setEmotionTag(EmotionCode.convertToValue(emotionStr));
            emotionDAO.save(emotion);
        }
        return emotionList;
    }

    private CompletionRequest emotionRequest(String prompt) {
        String mood = "平靜、快樂、狂喜、友愛、接受、信任、崇敬、屈服、擔心、恐懼、驚悚、敬畏、不解、驚訝、驚愕、反對、傷感、悲傷、悲痛、懊悔、厭倦、厭惡、憎恨、鄙夷、不耐煩、生氣、暴怒、挑釁、關心、期待、警惕、樂觀";
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt + "\n請從以下 " + mood + " 這幾個情緒中判斷出這句話主要呈現哪5種情緒")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
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

    @Override
    public List<String> searchByTeacher(String id) {
        return summaryRecordDAO.findByTeacher(id);
    }

    @Override
    public List<SummaryRecordBean> searchByTeacherIsNull(Integer level) {
        return CollectionUtils.map(level == 0 ?
                        summaryRecordDAO.findByTeacherIsNull() :
                        summaryRecordDAO.findByTeacherIsNullAndLevel(level),
                summaryRecordTransformer::transferToBean);
    }

    @Override
    public List<SummaryRecordBean> searchByTeacherIsNotNull(Integer level) {
        return CollectionUtils.map(level == 0 ?
                        summaryRecordDAO.findByTeacherIsNotNull() :
                        summaryRecordDAO.findByTeacherIsNotNullAndLevel(level),
                summaryRecordTransformer::transferToBean);
    }

    @Override
    public List<SummaryRecordBean> searchSummaryRecordList(String userId) {
        String teacher = SecurityUtils.getLoginUserAccount();
        return CollectionUtils.map(summaryRecordDAO.findByUserIdAndTeacherOrderByEstablishTimeDesc(userId, teacher),
                summaryRecordTransformer::transferToBean);
    }

    private CompletionRequest topicRequest(String prompt) {
        String topic = "「自我探索」、「情感困擾」、「家庭關係」、「學業學習」、「生理健康」、「心理測驗」、「生涯探索與規劃」、「心理疾患或傾向」、「情緒管理」、「人際關係」、「生活適應」、「網路沉迷」";
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt + "請從以下" + topic + "這幾個分類判斷出這句話屬於哪幾個分類")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
    }
}