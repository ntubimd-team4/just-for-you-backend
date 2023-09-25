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
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.array.ArrayData;
import tw.edu.ntub.imd.justforyou.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

import java.time.LocalDateTime;
import java.util.List;
// TODO 2個API都要再修改，第二層迴圈

@Tag(name = "推薦紀錄 /recommend-record")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/recommend-record")
public class RecommendRecordController {
    private final RecommendRecordService recommendRecordService;

    @Operation(summary = "全查(目前無分頁)", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "")
    public ResponseEntity<String> searchUserRecommendRecord() {
        ArrayData arrayData = new ArrayData();
        String userId = SecurityUtils.getLoginUserAccount();
        List<LocalDateTime> recommendRecordBeans = recommendRecordService.searchByUserId(userId);
        for (LocalDateTime recommendRecordBean : recommendRecordBeans) {
            ObjectData objectData = arrayData.addObject();
            objectData.add("establishTime", recommendRecordBean);

            List<RecommendRecordBean> timeList = recommendRecordService.searchByEstablishTime(userId, recommendRecordBean);
            addMusicListToObjectData(objectData, timeList);
        }
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    private void addMusicListToObjectData(ObjectData objectData, List<RecommendRecordBean> list) {
        CollectionObjectData data = objectData.createCollectionData();
        data.add("playList", list,
                (contentData, content) -> {
                    contentData.add("rid", content.getRid());
                    contentData.add("song", content.getSong());
                    contentData.add("link", content.getLink());
                    contentData.add("emotionTag", content.getEmotionTag());
                    contentData.add("isCollection", content.getCollection());
                });
    }


    @Operation(summary = "推薦紀錄 - 按標籤類型查詢", description = "wireframe pdf第5頁畫面")
    @GetMapping(path = "/tag")
    public ResponseEntity<String> searchTag(@RequestParam(name = "tag") Integer tag) {
        ArrayData arrayData = new ArrayData();
        String userId = SecurityUtils.getLoginUserAccount();
        List<LocalDateTime> recommendRecordBeans = recommendRecordService.searchByUserIdAndEmotionTag(userId, tag);
        for (LocalDateTime recommendRecordBean : recommendRecordBeans) {
            ObjectData objectData = arrayData.addObject();
            objectData.add("establishTime", recommendRecordBean);

            List<RecommendRecordBean> timeList = recommendRecordService.searchByEstablishTime(userId, recommendRecordBean, tag);
            addMusicListToObjectData(objectData, timeList);
        }
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }
}