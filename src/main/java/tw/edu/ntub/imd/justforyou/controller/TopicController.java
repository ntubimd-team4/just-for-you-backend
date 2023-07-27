package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.TopicBean;
import tw.edu.ntub.imd.justforyou.service.TopicService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    private final TopicService topicService;

    @PatchMapping(path = "")
    public ResponseEntity<String> updateTopicTag(@RequestBody TopicBean topicBean) {
        topicService.update(topicBean.getTid(), topicBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @DeleteMapping(path = "/{tid}")
    public ResponseEntity<String> deleteTopicTag(@PathVariable(name = "tid") Integer tid) {
        topicService.delete(tid);
        return ResponseEntityBuilder.success()
                .message("刪除成功")
                .build();
    }
}