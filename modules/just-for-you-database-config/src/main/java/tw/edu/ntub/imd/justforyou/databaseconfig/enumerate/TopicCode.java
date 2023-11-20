package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

public enum TopicCode {
    SELF_EXPLORATION(0, "自我探索"),
    EMOTIONAL_DISTRESS(1, "情感困擾"),
    FAMILY_RELATIONSHIP(2, "家庭關係"),
    MENTAL_ILLNESS_OR_TENDENCY(3, "心理疾患或傾向"),
    EMOTIONAL_MANAGEMENT(4, "情緒管理"),
    INTERPERSONAL_RELATIONSHIP(5, "人際關係"),
    ACADEMIC_STUDY(6, "學業學習"),
    CAREER_EXPLORATION_AND_PLANNING(7, "生涯探索與規劃"),
    LIFE_ADAPTATION(8, "生活適應"),
    INTERNET_ADDICTION(9, "網路沉迷"),
    PHYSICAL_HEALTH(10, "生理健康"),
    PSYCHOLOGICAL_TEST(11, "心理測驗");

    @Getter
    private final Integer value;
    @Getter
    private final String description;

    TopicCode(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Integer of(String description) {
        for (TopicCode topicCode : TopicCode.values()) {
            if (topicCode.getDescription().equals(description)) {
                return topicCode.getValue();
            }
        }
        return null;
    }

    public static String of(Integer topic) {
        for (TopicCode topicCode : TopicCode.values()) {
            if (topicCode.getValue().equals(topic)) {
                return topicCode.getDescription();
            }
        }
        return null;
    }
}