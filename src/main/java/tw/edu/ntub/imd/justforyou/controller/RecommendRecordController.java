package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.array.ArrayData;
import tw.edu.ntub.imd.justforyou.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "推薦紀錄 /recommend-record")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/recommend-record")
public class RecommendRecordController {
    private final RecommendRecordService recommendRecordService;

    @Operation(summary = "全查(目前無分頁)", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "")
    public ResponseEntity<String> searchUserRecommendRecord() {
        String userId = SecurityUtils.getLoginUserAccount();
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(recommendRecordService.searchByEstablishTime(userId), this::addMusicListToObjectData)
                .build();
    }

    private void addMusicListToObjectData(ObjectData objectData, RecommendRecordBean recommendRecordBean) {
        objectData.add("rid", recommendRecordBean.getRid());
        objectData.add("song", recommendRecordBean.getSong());
        objectData.add("thumbnails", recommendRecordBean.getThumbnails());
        objectData.add("link", recommendRecordBean.getLink());
        objectData.add("emotion_tag", recommendRecordBean.getEmotionTag());
        objectData.add("description", EmotionCode.convertToDescription(recommendRecordBean.getEmotionTag()));
        objectData.add("isCollection", recommendRecordBean.getCollection());
    }

    @Operation(summary = "推薦紀錄 - 按標籤類型查詢", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "/tag")
    public ResponseEntity<String> searchTag(@RequestParam(name = "tag") Integer tag) {
        String userId = SecurityUtils.getLoginUserAccount();
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(recommendRecordService.searchByEstablishTime(userId, tag), this::addMusicListToObjectData)
                .build();
    }

    @Operation(summary = "推薦紀錄 - 模糊查詢", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "/query")
    public ResponseEntity<String> querySong(@RequestParam(name = "song") String song) {
        String userId = SecurityUtils.getLoginUserAccount();
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(recommendRecordService.searchByEstablishTime(userId, song), this::addMusicListToObjectData)
                .build();
    }

    @Operation(summary = "使用者有的情緒標籤", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "/emotion-tag")
    public ResponseEntity<String> searchEmotionTag() {
        return ResponseEntityBuilder.success()
                .data(recommendRecordService.searchUserEmotionTag(SecurityUtils.getLoginUserAccount()),
                        this::addEmotionTagListToObjectData)
                .message("查詢成功")
                .build();
    }

    private void addEmotionTagListToObjectData(ObjectData objectData, Integer emotionTag) {
        objectData.add("emotion_tag", emotionTag);
        objectData.add("description", EmotionCode.convertToDescription(emotionTag));
    }
}