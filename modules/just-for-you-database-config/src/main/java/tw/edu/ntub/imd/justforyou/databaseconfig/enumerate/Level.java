package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

public enum Level {
    NONE(0, "0", "零", "無法辨識"),
    LEVEL_ONE(1, "1", "一", "第一級"),
    LEVEL_TWO(2, "2", "二", "第二級"),
    LEVEL_THREE(3, "3", "三", "第三級"),
    LEVEL_FOUR(4, "4", "四", "第四級"),
    LEVEL_THREE_TO_TEACHER(5, "5", "五", "個管師分配給諮商師三級狀態個案");

    @Getter
    private final Integer level;
    @Getter
    private final String levelStr;
    @Getter
    private final String levelChar;
    @Getter
    private final String levelName;

    Level(Integer level, String levelStr, String levelChar, String levelName) {
        this.level = level;
        this.levelStr = levelStr;
        this.levelChar = levelChar;
        this.levelName = levelName;
    }

    public static Level of(Integer id) {
        for (Level level : Level.values()) {
            if (level.getLevel().equals(id)) {
                return level;
            }
        }
        return null;
    }
}