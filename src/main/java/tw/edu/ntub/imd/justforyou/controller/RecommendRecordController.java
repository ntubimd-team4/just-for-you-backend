package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

@Tag(name = "推薦紀錄 /recommend-record")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/recommend-record")
public class RecommendRecordController {
    private final RecommendRecordService recommendRecordService;

    @Operation(summary = "推薦紀錄 - 全查(目前無分頁)", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "/data")
    public ResponseEntity<String> searchUserRecommendRecord() {
        String userId = SecurityUtils.getLoginUserAccount();
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(recommendRecordService.searchByUserId(userId), this::addUserRecommendRecordToObjectData)
                .build();
    }

    private void addUserRecommendRecordToObjectData(ObjectData objectData, RecommendRecordBean recommendRecordBean) {
        objectData.add("sid", recommendRecordBean.getSid());
        objectData.add("userId", recommendRecordBean.getUserId());
        objectData.add("establishTime", recommendRecordBean.getEstablishTime());
        objectData.add("rid", recommendRecordBean.getRid());
        objectData.add("song", recommendRecordBean.getSong());
        objectData.add("link", recommendRecordBean.getLink());
        objectData.add("emotionTag", recommendRecordBean.getEmotionTag());
        objectData.add("description", recommendRecordBean.getDescription());
        objectData.add("collection", recommendRecordBean.getCollection());
    }
}