package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.bean.SummaryRecordBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.service.SummaryRecordService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/summary_record")
public class SummaryRecordController {
    private final SummaryRecordService summaryRecordService;

    @PostMapping(path = "")
    public ResponseEntity<String> openAi(@RequestBody SummaryRecordBean summaryRecordBean) {
        String prompt = summaryRecordBean.getPrompt();
        Integer sid = summaryRecordService.saveSummaryRecord(summaryRecordBean);
        List<String> emotionList = summaryRecordService.saveEmotion(sid, prompt);
        summaryRecordService.saveTopic(sid, prompt);

        List<String> colorList = new ArrayList<>();
        for (String emotionStr : emotionList) {
            colorList.add(EmotionCode.transformerToColor(emotionStr));
        }
        List<String> colors = colorList.stream().distinct().collect(Collectors.toList());


        Map<String, List<String>> data = new HashMap<>();
        for (String color : colors) {
            List<String> emotion = EmotionCode.transformerToEmotion(color, emotionList);
            data.put(color, emotion);
        }

        return ResponseEntityBuilder.success()
                .message("摘要成功")
                .data(data)
                .build();
    }
}