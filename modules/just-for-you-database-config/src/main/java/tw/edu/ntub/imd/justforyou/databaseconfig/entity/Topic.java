package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.TopicListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 諮商主題表
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(TopicListener.class)
@Table(name = "topic", schema = Config.DATABASE_NAME)
public class Topic {
    /**
     * 流水號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id", nullable = false, unique = true)
    private Integer tid;
    /**
     * 對應summary_record的s_id
     *
     * @since 1.0.0
     */
    @Column(name = "s_id", nullable = false)
    private Integer sid;
    /**
     * 諮商主題標籤（對應codelist表中topic的value）
     *
     * @since 1.0.0
     */
    @Column(name = "topic", nullable = false)
    private Integer topic;
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