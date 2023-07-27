package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicBean {
    private Integer tid;
    private Integer sid;
    private Integer topic;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;
}