package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.bean.RecommendRecordBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.service.MusicRecommendService;
import tw.edu.ntub.imd.justforyou.service.RecommendRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

@Tag(name = "收藏 /collection")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/collection")
public class CollectionController {
    private final MusicRecommendService musicRecommendService;
    private final RecommendRecordService recommendRecordService;

    @Operation(summary = "收藏查詢")
    @GetMapping(path = "")
    public ResponseEntity<String> searchCollection() {
        String userId = SecurityUtils.getLoginUserAccount();
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(recommendRecordService.searchCollectionIsTrue(userId), this::addMusicListToObjectData)
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

    @Operation(summary = "音樂收藏 - 修改收藏狀態")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateCollection(@RequestBody MusicRecommendBean musicRecommendBean) {
        String msg = musicRecommendService.updateCollection(musicRecommendBean.getRid());
        return ResponseEntityBuilder.success()
                .message(msg)
                .build();
    }
}