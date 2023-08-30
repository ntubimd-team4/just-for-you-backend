package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.MusicListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 音樂表
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(MusicListener.class)
@Table(name = "music", schema = Config.DATABASE_NAME)
public class Music {
    /**
     * 流水號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id", nullable = false, unique = true)
    private Integer mid;
    /**
     * 歌曲
     *
     * @since 1.0.0
     */
    @Column(name = "song", nullable = false, length = 55)
    private String song;
    /**
     * youtube連結
     *
     * @since 1.0.0
     */
    @Column(name = "link", nullable = false, length = 55)
    private String link;
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