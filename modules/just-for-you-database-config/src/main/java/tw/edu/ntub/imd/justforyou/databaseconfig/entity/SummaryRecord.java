package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.SummaryRecordListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 摘要紀錄
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(SummaryRecordListener.class)
@Table(name = "summary_record", schema = Config.DATABASE_NAME)
public class SummaryRecord {
    /**
     * 流水號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id", nullable = false, unique = true)
    private Integer sid;
    /**
     * 使用者Google帳號
     *
     * @since 1.0.0
     */
    @Column(name = "user_id", length = 45, nullable = false)
    private String userId;
    /**
     * 內容
     *
     * @since 1.0.0
     */
    @Column(name = "content", nullable = false)
    private String content;
    /**
     * 內容摘要
     *
     * @since 1.0.0
     */
    @Column(name = "summary")
    private String summary;
    /**
     * 對話建立時間(時間軸)
     *
     * @since 1.0.0
     */
    @Column(name = "establish_time", nullable = false)
    private LocalDateTime establishTime;
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