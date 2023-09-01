package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.TopicBean;
import tw.edu.ntub.imd.justforyou.service.TopicService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@Tag(name = "諮商主題標籤 /topic")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    private final TopicService topicService;

    @Operation(summary = "修改諮商主題標籤")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateTopicTag(@RequestBody TopicBean topicBean) {
        topicService.update(topicBean.getTid(), topicBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @Operation(summary = "刪除諮商主題標籤")
    @DeleteMapping(path = "/{tid}")
    public ResponseEntity<String> deleteTopicTag(@PathVariable(name = "tid") Integer tid) {
        topicService.delete(tid);
        return ResponseEntityBuilder.success()
                .message("刪除成功")
                .build();
    }
}