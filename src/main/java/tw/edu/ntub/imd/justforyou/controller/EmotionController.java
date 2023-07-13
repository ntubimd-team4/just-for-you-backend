package tw.edu.ntub.imd.justforyou.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
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

    //情緒整筆刪除
    @DeleteMapping(path = " ")
    public ResponseEntity<String> deleteEmotion(@RequestBody EmotionBean deemotionBean) {
        emotionService.delete(Integer.valueOf(deemotionBean.getEid()),deemotionBean);
        return ResponseEntityBuilder.success()
                .message("刪除成功")
                .build();
    }



}
