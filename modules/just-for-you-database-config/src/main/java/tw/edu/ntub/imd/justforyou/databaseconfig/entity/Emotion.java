package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.EmotionListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 情緒標籤表
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(EmotionListener.class)
@Table(name = "emotion", schema = Config.DATABASE_NAME)
public class Emotion {
    /**
     * 流水號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "e_id", nullable = false, unique = true)
    private Integer eid;
    /**
     * 對應summary_record的s_id
     *
     * @since 1.0.0
     */
    @Column(name = "s_id", nullable = false)
    private Integer sid;
    /**
     * 情緒標籤（對應codelist表中emotion_tag的value）
     *
     * @since 1.0.0
     */
    @Column(name = "emotion_tag", nullable = false)
    private Integer emotionTag;
    /**
     * 啟用狀態(啟用:1/不啟用:0)
     *
     * @since 1.0.0
     */
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "available", nullable = false)
    private Boolean available;
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