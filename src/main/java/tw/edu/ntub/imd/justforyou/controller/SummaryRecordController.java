package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.service.SummaryRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
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
        String music = remoteSymbol(emotionService.recommendMusic(sid));


        ObjectData objectData = new ObjectData();
        objectData.add("value", value);
        objectData.add("music", music);

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
}