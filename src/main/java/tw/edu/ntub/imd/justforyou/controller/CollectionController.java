package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.service.MusicRecommendService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@Tag(name = "收藏 /collection")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/collection")
public class CollectionController {
    private final MusicRecommendService musicRecommendService;

    @Operation(summary = "音樂收藏 - 修改收藏狀態")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateCollection(@RequestBody MusicRecommendBean musicRecommendBean) {
        String msg = musicRecommendService.updateCollection(musicRecommendBean.getRid());
        return ResponseEntityBuilder.success()
                .message(msg)
                .build();
    }
}