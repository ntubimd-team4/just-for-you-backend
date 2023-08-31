package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.service.SummaryRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/summary_record")
public class SummaryRecordController {
    private final SummaryRecordService summaryRecordService;
    private final EmotionService emotionService;

    @PostMapping(path = "")
    public ResponseEntity<String> openAi(@RequestBody SummaryRecordBean summaryRecordBean) {
        String prompt = summaryRecordBean.getPrompt();
        Integer sid = summaryRecordService.saveSummaryRecord(summaryRecordBean);
        List<String> emotionList = summaryRecordService.saveEmotion(sid, prompt);
        summaryRecordService.saveTopic(sid, prompt);

//        List<String> colorList = new ArrayList<>(); //Todo 顏色格式，待復原
//        for (String emotionStr : emotionList) {
//            colorList.add(EmotionCode.transformerToColor(emotionStr));
//        }
//        List<String> colors = colorList.stream().distinct().collect(Collectors.toList());


//        Map<String, List<String>> data = new HashMap<>();
//        for (String color : colors) {
//            List<String> emotion = EmotionCode.transformerToEmotion(color, emotionList);
//            data.put(color, emotion);
//        }

        String value = remoteSymbol(emotionList);
        List<Music> musicList = emotionService.recommendMusic(sid);


        ObjectData objectData = new ObjectData();
        objectData.add("value", value);
        addMusicListToObjectData(objectData, musicList);

        return ResponseEntityBuilder.success()
                .message("摘要成功")
//                .data(SingleValueObjectData.create("value", remoteEnd)) // TODO 一評摘要API回傳格式
                .data(objectData)
                .build();
    }

    private String remoteSymbol(List<String> list) {
        String remoteStart = StringUtils.removeStart(list.toString(), "[");
        return StringUtils.removeEnd(remoteStart, "]");
    }

    private void addMusicListToObjectData(ObjectData objectData, List<Music> list) {
        CollectionObjectData data = objectData.createCollectionData();
        data.add("musicList", list,
                (contentData, content) -> {
                    contentData.add("mid", content.getMid());
                    contentData.add("song", content.getSong());
                    contentData.add("link", content.getLink());
                });
    }
    public ResponseEntity<String> getSummaryRecord(@RequestParam(name = "sid") String id) {
        ObjectData objectData = new ObjectData();
        SummaryRecordBean summaryRecordBean = summaryRecordService.getById(Integer.valueOf(id)).orElseThrow(() -> new NotFoundException("查無此摘要，請確認是否正確"));
        objectData.add("sid", summaryRecordBean.getSid());
        objectData.add("userId", summaryRecordBean.getUserId());
        objectData.add("summary", summaryRecordBean.getSummary());
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }
}