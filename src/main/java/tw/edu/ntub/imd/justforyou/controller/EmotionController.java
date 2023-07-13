package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/emotion")
public class EmotionController {
    private final EmotionService emotionService;


    //修改情緒
    @PatchMapping(path = "")
    public ResponseEntity<String> updateEmotion(@RequestBody EmotionBean emotionBean) {
        emotionService.update(Integer.valueOf(emotionBean.getEid()),emotionBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @DeleteMapping(path = "/{eId}")
    public ResponseEntity<String> deleteEmotion(@PathVariable(name = "eId") Integer eId) {
        EmotionBean emotionBean = emotionService.getById(eId)
                .orElseThrow(() -> new NotFoundException("找不到資料, eId = " + eId));
        emotionBean.setAvailable(!emotionBean.getAvailable());
        emotionService.update(eId, emotionBean);
        return ResponseEntityBuilder.success()
                .message("刪除成功")
                .build();
    }
}