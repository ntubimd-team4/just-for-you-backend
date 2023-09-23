package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "音樂推薦 /music-recommend")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/music-recommend")
public class MusicRecommendController {
}