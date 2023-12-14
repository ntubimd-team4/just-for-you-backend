package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Level;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.*;
import tw.edu.ntub.imd.justforyou.util.data.ObjectDataUtils;
import tw.edu.ntub.imd.justforyou.util.data.SymbolUtils;
import tw.edu.ntub.imd.justforyou.util.encryption.EncryptionUtils;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

import java.util.List;
import java.util.Objects;

@Tag(name = "摘要相關 /summary-record")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/summary-record")
public class SummaryRecordController {
    private final SummaryRecordService summaryRecordService;
    private final ConsultationRecordService consultationRecordService;
    private final UserAccountService userAccountService;
    private final EmotionService emotionService;
    private final TopicService topicService;

    @Operation(summary = "輸入心情小語", description = "wireframe pdf第3頁畫面")
    @PostMapping(path = "")
    public ResponseEntity<String> openAi(@RequestBody SummaryRecordBean summaryRecordBean) {
        String prompt = summaryRecordBean.getPrompt();
        String[] summaryRecord = summaryRecordService.saveSummaryRecord(summaryRecordBean).split(",");
        Integer sid = Integer.parseInt(summaryRecord[0]);
        String level = summaryRecord[1];
        List<String> emotionList = summaryRecordService.saveEmotion(sid, prompt);
        summaryRecordService.saveTopic(sid, prompt);

        String value = SymbolUtils.remoteSymbol(emotionList);
        List<MusicEmotion> musicEmotionList = emotionService.searchMusic(sid);
        List<RecommendRecord> recommendRecordList = emotionService.recommendMusic(sid, musicEmotionList);
        String text = emotionService.generateText(musicEmotionList);

        ObjectData objectData = new ObjectData();
        objectData.add("sid", sid);
        objectData.add("isHighLevel", level.equals(Level.LEVEL_THREE.getLevelStr()) || level.equals(Level.LEVEL_FOUR.getLevelStr()));
        objectData.add("text", text);
        objectData.add("value", value);
        objectData.add("color", EmotionCode.transformerToColor(value.split(",")[0]));
        ObjectDataUtils.addMusicListToObjectData(objectData, recommendRecordList);

        return ResponseEntityBuilder.success()
                .message("摘要成功")
                .data(objectData)
                .build();
    }

    @Operation(summary = "時間軸、摘要紀錄查詢", description = "wireframe pdf第9頁")
    @GetMapping(path = "")
    public ResponseEntity<String> searchSummaryRecord(@RequestParam(name = "userId") String userId) {
        ObjectData objectData = new ObjectData();
        addUserAccountToObjectData(userId, objectData);
        addSummaryListToObjectData(objectData, summaryRecordService.searchSummaryRecordList(userId));
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    private void addUserAccountToObjectData(String userId, ObjectData objectData) {
        UserAccountBean userAccountBean = userAccountService.getById(userId)
                .orElseThrow(() -> new NotFoundException("查無此學生"));
        objectData.add("userId", userAccountBean.getUserId());
        objectData.add("userName", userAccountBean.getUserName());
        objectData.add("department", userAccountBean.getDepartment());
        objectData.add("role", userAccountBean.getRole().getTypeName());
    }

    private void addSummaryListToObjectData(ObjectData objectData, List<SummaryRecordBean> list) {
        CollectionObjectData data = objectData.createCollectionData();
        data.add("summaryRecordList", list, this::addSummaryObject);
    }

    private void addSummaryObject(ObjectData objectData, SummaryRecordBean summaryRecordBean) {
        objectData.add("sid", summaryRecordBean.getSid());
        objectData.add("content", EncryptionUtils.decryptText(summaryRecordBean.getContent()));
        objectData.add("summary", EncryptionUtils.decryptText(summaryRecordBean.getSummary()));
        objectData.add("level", Objects.requireNonNull(Level.of(summaryRecordBean.getLevel())).getLevelName());
        objectData.add("establishTime", summaryRecordBean.getEstablishTime().toString().substring(0, 16).replace("T", " "));
        objectData.add("emotion", addEmotionToObjectData(summaryRecordBean.getSid()));
        objectData.add("topic", addTopicToObjectData(summaryRecordBean.getSid()));
    }

    private String addTopicToObjectData(Integer sid) {
        return topicService.searchBySid(sid);
    }

    @Operation(summary = "單查時間軸、摘要紀錄詳細資訊", description = "wireframe pdf最後一頁")
    @GetMapping(path = "/{sid}")
    public ResponseEntity<String> getSummaryRecord(@PathVariable(name = "sid") Integer sid) {
        ObjectData objectData = new ObjectData();
        SummaryRecordBean summaryRecordBean = summaryRecordService.getById(sid)
                .orElseThrow(() -> new NotFoundException("查無此摘要，請確認是否正確"));
        addUserAccountToObjectData(summaryRecordBean.getUserId(), objectData);
        addSummaryObject(objectData, summaryRecordBean);
        addConsultationListToObjectData(objectData, consultationRecordService.getBySid(summaryRecordBean.getSid()));
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    private void addConsultationListToObjectData(ObjectData objectData, ConsultationRecordBean consultationRecordBean) {
        objectData.add("consultationContent", EncryptionUtils.decryptText(consultationRecordBean.getContent()));
        objectData.add("createId", consultationRecordBean.getCreateId());
        objectData.add("createTime", consultationRecordBean.getCreateTime());
    }

    @Operation(summary = "全部摘要紀錄", description = "個案管理師分配頁")
    @GetMapping(path = "/list")
    public ResponseEntity<String> searchAllSummaryRecord(@RequestParam("level") Integer level,
                                                         @RequestParam("assign") Integer assign) {
        List<SummaryRecordBean> summaryRecordBeanList =
                assign == 0 ?
                        summaryRecordService.searchByTeacherIsNull(level) :
                        summaryRecordService.searchByTeacherIsNotNull(level);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(summaryRecordBeanList, this::addAllSummaryObject)
                .build();
    }

    private void addAllSummaryObject(ObjectData objectData, SummaryRecordBean summaryRecordBean) {
        objectData.add("sid", summaryRecordBean.getSid());
        objectData.add("userId", summaryRecordBean.getUserId());
        objectData.add("userName", userAccountService.getById(summaryRecordBean.getUserId()).get().getUserName());
        objectData.add("summary", EncryptionUtils.decryptText(summaryRecordBean.getSummary()));
        objectData.add("establishTime", summaryRecordBean.getEstablishTime().toString().substring(0, 16).replace("T", " "));
        objectData.add("teacher", StringUtils.isNotBlank(summaryRecordBean.getTeacher()) ?
                userAccountService.getById(summaryRecordBean.getTeacher()).get().getUserName() : null);
        objectData.add("level", Objects.requireNonNull(Level.of(summaryRecordBean.getLevel())).getLevelName());
        objectData.add("topic", addTopicToObjectData(summaryRecordBean.getSid()));
        objectData.add("emotion", addEmotionToObjectData(summaryRecordBean.getSid()));
    }

    private String addEmotionToObjectData(Integer sid) {
        return emotionService.searchBySid(sid);
    }

    @Operation(summary = "分配學生給諮商師")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateTeacher(@RequestBody SummaryRecordBean summaryRecordBean) {
        summaryRecordService.update(summaryRecordBean.getSid(), summaryRecordBean);
        return ResponseEntityBuilder.success()
                .message("分配成功")
                .build();
    }
}