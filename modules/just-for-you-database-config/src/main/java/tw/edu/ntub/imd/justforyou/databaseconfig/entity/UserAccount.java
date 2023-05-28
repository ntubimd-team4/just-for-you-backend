package tw.edu.ntub.imd.justforyou.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.Config;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.listener.UserAccountListener;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 帳號表
 *
 * @since 1.0.0
 */
@Data
@Entity
@EntityListeners(UserAccountListener.class)
@Table(name = "user_account", schema = Config.DATABASE_NAME)
public class UserAccount {
    /**
     * 使用者Google帳號
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "user_id", length = 45, nullable = false, unique = true)
    private String userId;
    /**
     * 使用者名稱
     *
     * @since 1.0.0
     */
    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;
    /**
     * 性別(男:0/女:1)
     *
     * @since 1.0.0
     */
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "user_sex", length = 1)
    private Boolean userSex;
    /**
     * 所屬科系/班級
     *
     * @since 1.0.0
     */
    @Column(name = "department", length = 45)
    private String department;
    /**
     * 頭像
     *
     * @since 1.0.0
     */
    @Column(name = "picture", length = 200)
    private String picture;
    /**
     * google id
     *
     * @since 1.0.0
     */
    @Column(name = "google_id", length = 25)
    private String googleId;
    /**
     * 角色/權限(對應codelist)
     *
     * @since 1.0.0
     */
    @Column(name = "role", length = 1, nullable = false)
    private Role role;
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