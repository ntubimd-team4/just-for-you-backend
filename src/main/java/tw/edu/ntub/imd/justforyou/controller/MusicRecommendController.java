package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<String> updateCollection(@RequestParam("id") String id) {
        musicRecommendService.updateCollection(id);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }
}
