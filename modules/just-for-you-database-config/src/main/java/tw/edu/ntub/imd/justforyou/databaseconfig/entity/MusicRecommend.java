package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.converter.BooleanTo1And0Converter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 音樂推薦表
 *
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "music_recommend", schema = Config.DATABASE_NAME)
public class MusicRecommend {
    /**
     * 流水號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id", nullable = false, unique = true)
    private Integer rid;
    /**
     * 對應summary_record表的s_id
     *
     * @since 1.0.0
     */
    @Column(name = "s_id", nullable = false)
    private Integer sid;
    /**
     * 對應music表的m_id
     *
     * @since 1.0.0
     */
    @Column(name = "m_id", nullable = false)
    private Integer mid;
    /**
     * 是否收藏(是:1/否:0)
     *
     * @since 1.0.0
     */
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "collection", nullable = false)
    private Boolean collection;
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