package tw.edu.ntub.imd.justforyou.databaseconfig.entity.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.converter.BooleanTo1And0Converter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 推薦紀錄整合表
 *
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "v_recommend_record", schema = Config.DATABASE_NAME)
@Immutable
public class RecommendRecord {
    /**
     * UUID流水號
     */
    @Id
    @Column(name = "id",nullable = false,unique = true)
    private String id;
    /**
     * 摘要紀錄流水號
     *
     * @since 1.0.0
     */
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
     * 對話建立時間(時間軸)
     *
     * @since 1.0.0
     */
    @Column(name = "establish_time", nullable = false)
    private LocalDateTime establishTime;
    /**
     * 推薦紀錄流水號
     *
     * @since 1.0.0
     */
    @Column(name = "r_id", nullable = false, unique = true)
    private Integer rid;
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
     * 影片縮圖
     *
     * @since 1.0.0
     */
    @Column(name = "thumbnails", length = 80)
    private String thumbnails;
    /**
     * 情緒標籤（對應codelist表中emotion_tag的value）
     *
     * @since 1.0.0
     */
    @Column(name = "emotion_tag", nullable = false)
    private Integer emotionTag;
    /**
     * 對應名稱
     *
     * @since 1.0.0
     */
    @Column(name = "description", length = 45, nullable = false)
    private String description;
    /**
     * 是否收藏(是:1/否:0)
     *
     * @since 1.0.0
     */
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "collection", nullable = false)
    private Boolean collection;
}