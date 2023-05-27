package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

public enum Role {
    ADMIN("0", "最高管理者"),
    TEACHER("1", "諮商師"),
    STUDENT("2", "學生");

    @Getter
    private final String value;
    @Getter
    private final String typeName;

    Role(String value, String typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    public static String getAuthorityName(Role role) {
        return role.getTypeName();
    }

    public static boolean isTeacher(String identity) {
        return identity.equals(Role.getAuthorityName(Role.TEACHER));
    }

    public static boolean isStudent(String identity) {
        return identity.equals(Role.getAuthorityName(Role.STUDENT));
    }

    public static boolean isManage(String identity) {
        return identity.equals(Role.getAuthorityName(Role.ADMIN));
    }
}