package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

public enum Role {
    CASE_MANAGEMENT("0", "個案管理師"),
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

    public static Role of(String value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
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

    public static boolean isCaseManage(String identity) {
        return identity.equals(Role.getAuthorityName(Role.CASE_MANAGEMENT));
    }
}