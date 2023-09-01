package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@Tag(name = "情緒標籤 /emotion")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/emotion")
public class EmotionController {
    private final EmotionService emotionService;

    @Operation(summary = "修改情緒標籤")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateEmotion(@RequestBody EmotionBean emotionBean) {
        emotionService.update(emotionBean.getEid(), emotionBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @Operation(summary = "刪除情緒標籤")
    @DeleteMapping(path = "/{eid}")
    public ResponseEntity<String> deleteEmotion(@PathVariable(name = "eid") Integer eid) {
        EmotionBean emotionBean = emotionService.getById(eid)
                .orElseThrow(() -> new NotFoundException("找不到資料, eid = " + eid));
        emotionBean.setAvailable(!emotionBean.getAvailable());
        emotionService.update(eid, emotionBean);
        return ResponseEntityBuilder.success()
                .message("刪除成功")
                .build();
    }
}