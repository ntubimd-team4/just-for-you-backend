package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MusicEmotionBean {
    private Integer id;
    private Integer mid;
    private Integer emotionTag;
    private Boolean available;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;
}