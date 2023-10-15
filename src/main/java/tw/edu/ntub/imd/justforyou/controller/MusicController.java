package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.justforyou.service.MusicService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

@Tag(name = "音樂 /music")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/music")
public class MusicController {
    private final MusicService musicService;

    @Operation(summary = "音樂連結情緒標籤(後端用，不用串這個API)")
    @PostMapping(path = "")
    public ResponseEntity<String> searchMusic(@RequestParam("file") MultipartFile file) {
        musicService.searchMusic(file);
        return ResponseEntityBuilder.success()
                .message("成功")
                .build();
    }
}