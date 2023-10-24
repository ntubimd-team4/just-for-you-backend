package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

public enum Level {
    NONE(0, "無法辨識"),
    LEVEL_ONE(1, "第一級"),
    LEVEL_TWO(2, "第二級"),
    LEVEL_THREE(3, "第三級"),
    LEVEL_FOUR(4, "第四級");

    @Getter
    private final Integer level;
    @Getter
    private final String levelName;

    Level(Integer level, String levelName) {
        this.level = level;
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