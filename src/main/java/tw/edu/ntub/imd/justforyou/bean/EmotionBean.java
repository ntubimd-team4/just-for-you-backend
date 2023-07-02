package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmotionBean extends TopicBean{
    private Integer eid;
    private String sid;
    private String emotionTag;
    private Boolean available;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;
}