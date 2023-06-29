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

    //修改諮商主題標籤
    @PatchMapping(path = "")
    public ResponseEntity<String> updateTopic(@RequestBody TopicBean topicBean) {
        topicService.update(topicBean.getTId(),topicBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }


}
