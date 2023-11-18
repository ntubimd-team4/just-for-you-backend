package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SummaryRecordBean {
    private Integer sid;
    private String userId;
    private String content;
    private String summary;
    private LocalDateTime establishTime;
    private String teacher;
    private Integer level;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;

    private String prompt;
}