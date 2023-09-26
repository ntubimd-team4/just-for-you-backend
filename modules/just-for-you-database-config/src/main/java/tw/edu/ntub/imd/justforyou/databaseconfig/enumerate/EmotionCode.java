package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum EmotionCode {
    CALM("yellow", 0, "平靜"),
    HAPPY("yellow", 1, "快樂"),
    ECSTASY("yellow", 2, "狂喜"),
    FRIENDLINESS("yellow", 3, "友愛"),
    ACCEPT("green", 4, "接受"),
    TRUST("green", 5, "信任"),
    RESPECT("green", 6, "崇敬"),
    YIELD("green", 7, "屈服"),
    WORRY("teal", 8, "擔心"),
    FEAR("teal", 9, "恐懼"),
    THRILLER("teal", 10, "驚悚"),
    AWE("teal", 11, "敬畏"),
    PUZZLED("cyan", 12, "不解"),
    SURPRISE("cyan", 13, "驚訝"),
    CONSTERNATION("cyan", 14, "驚愕"),
    CONTRA("cyan", 15, "反對"),
    SAD("blue", 16, "傷感"),
    AFFLICTED("blue", 17, "悲傷"),
    GRIEF("blue", 18, "悲痛"),
    REGRET("blue", 19, "懊悔"),
    BORED("purple", 20, "厭倦"),
    DISGUST("purple", 21, "厭惡"),
    HATRED("purple", 22, "憎恨"),
    DISDAIN("purple", 23, "鄙夷"),
    IMPATIENT("red", 24, "不耐煩"),
    ANGRY("red", 25, "生氣"),
    FURY("red", 26, "暴怒"),
    PROVOCATIVE("red", 27, "挑釁"),
    CARE("orange", 28, "關心"),
    EXPECT("orange", 29, "期待"),
    ALERT("orange", 30, "警惕"),
    OPTIMISM("orange", 31, "樂觀");

    @Getter
    private final String color;
    @Getter
    private final Integer value;
    @Getter
    private final String description;

    EmotionCode(String color, Integer value, String description) {
        this.color = color;
        this.value = value;
        this.description = description;
    }

    public static Integer convertToValue(String description) {
        for (EmotionCode emotionCode : EmotionCode.values()) {
            if (emotionCode.getDescription().equals(description)) {
                return emotionCode.getValue();
            }
        }
        return null;
    }

    public static String convertToDescription(Integer value) {
        for (EmotionCode emotionCode : EmotionCode.values()) {
            if (emotionCode.getValue().equals(value)) {
                return emotionCode.getDescription();
            }
        }
        return null;
    }

    public static String transformerToColor(String description) {
        for (EmotionCode emotionCode : EmotionCode.values()) {
            if (emotionCode.getDescription().equals(description)) {
                return emotionCode.getColor();
            }
        }
        return null;
    }

    public static List<String> transformerToEmotion(String color, List<String> description) {
        List<String> emotionTagList = new ArrayList<>();
        for (EmotionCode emotionCode : EmotionCode.values()) {
            for (String s : description) {
                if (emotionCode.getColor().equals(color) && emotionCode.getDescription().equals(s)) {
                    emotionTagList.add(s);
                }
            }
        }
        return emotionTagList;
    }
}