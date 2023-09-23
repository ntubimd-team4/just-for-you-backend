package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.MusicRecommendBean;
import tw.edu.ntub.imd.justforyou.service.MusicRecommendService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@Tag(name = "音樂推薦 /music-recommend")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/music-recommend")
public class MusicRecommendController {
    private final MusicRecommendService musicRecommendService;

    @Operation(summary = "音樂收藏 - 修改收藏狀態")
    @PatchMapping(path = "/collection")
    public ResponseEntity<String> updateCollection(@RequestBody MusicRecommendBean musicRecommendBean) {
        musicRecommendService.updateCollection(musicRecommendBean.getRid());
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }
}