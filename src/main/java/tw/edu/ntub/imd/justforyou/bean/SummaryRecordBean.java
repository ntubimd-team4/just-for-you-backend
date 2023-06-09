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
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;

    private String model;
    private String prompt;
    private Integer maxTokens;
    private Double temperature;
}