package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MusicRecommendBean {
    private Integer rid;
    /**
     * @see tw.edu.ntub.imd.justforyou.databaseconfig.entity.SummaryRecord#sid
     */
    private Integer sid;
    /**
     * @see tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion#id
     */
    private Integer musicEmoId;
    private Boolean collection;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;
}