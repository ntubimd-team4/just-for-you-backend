package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.util.data.ObjectDataUtils;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

import java.util.List;

@Tag(name = "音樂推薦 /music-recommend")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/music-recommend")
public class MusicRecommendController {
    private final EmotionService emotionService;

    @Operation(summary = "重新推薦音樂")
    @GetMapping(path = "")
    public ResponseEntity<String> recommendMusicAgain(@RequestParam(name = "sid") Integer sid) {
        List<MusicEmotion> musicEmotionList = emotionService.searchMusic(sid);
        List<RecommendRecord> recommendRecordList = emotionService.recommendMusic(sid, musicEmotionList);
        String text = emotionService.generateText(musicEmotionList);
        ObjectData objectData = new ObjectData();
        objectData.add("text", text);
        ObjectDataUtils.addMusicListToObjectData(objectData, recommendRecordList);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }
}