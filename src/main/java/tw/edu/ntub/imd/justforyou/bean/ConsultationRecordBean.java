package tw.edu.ntub.imd.justforyou.bean;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ConsultationRecordBean {
    private Integer tId;
    private Integer sId;
    private String content;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;
}
