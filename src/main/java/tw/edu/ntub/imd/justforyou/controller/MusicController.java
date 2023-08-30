package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.service.MusicService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/music")
public class MusicController {
    private final MusicService musicService;

    @PostMapping(path = "")
    public ResponseEntity<String> searchMusic() {
        musicService.searchMusic();
        return ResponseEntityBuilder.success()
                .message("成功")
//                .data()
                .build();
    }
}
