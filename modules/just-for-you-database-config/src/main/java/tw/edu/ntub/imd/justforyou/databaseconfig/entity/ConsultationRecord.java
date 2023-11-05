package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.ConsultationRecordListener;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 諮商紀錄
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(ConsultationRecordListener.class)
@Table(name = "consultation_record", schema = Config.DATABASE_NAME)
public class ConsultationRecord {
    /**
     * 對應summary_record的s_id
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "s_id", nullable = false, unique = true)
    private Integer sid;
    /**
     * 內容
     *
     * @since 1.0.0
     */
    @Column(name = "content", nullable = false)
    private String content;
    /**
     * 新增者
     *
     * @since 1.0.0
     */
    @Column(name = "create_id", length = 45, nullable = false)
    private String createId;
    /**
     * 新增時間
     *
     * @since 1.0.0
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;
    /**
     * 修改者
     *
     * @since 1.0.0
     */
    @Column(name = "modify_id", length = 45)
    private String modifyId;
    /**
     * 修改時間
     *
     * @since 1.0.0
     */
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
}