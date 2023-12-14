package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecommendRecordBean {
    /**
     * @see tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend#rid
     */
    private Integer rid;
    /**
     * @see tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord#sid
     */
    private String userId;
    private LocalDateTime establishTime;
    /**
     * @see tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music#mid
     */
    private Integer mid;
    private String song;
    private String link;
    private String thumbnails;
    /**
     * @see tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion#emotionTag
     */
    private Integer emotionTag;
    private String description;
    private Boolean collection;
}