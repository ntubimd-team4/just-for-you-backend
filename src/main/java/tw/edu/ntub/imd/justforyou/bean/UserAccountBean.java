package tw.edu.ntub.imd.justforyou.bean;

import lombok.Data;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class UserAccountBean {
    private String userId;
    private String userName;
    private Boolean userSex;
    private String department;
    private String picture;
    private String googleId;
    private Role role;
    private Boolean available;
    private String createId;
    private LocalDateTime createTime;
    private String modifyId;
    private LocalDateTime modifyTime;


}