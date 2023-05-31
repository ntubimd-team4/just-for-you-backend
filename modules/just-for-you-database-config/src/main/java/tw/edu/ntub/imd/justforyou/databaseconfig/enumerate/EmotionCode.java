package tw.edu.ntub.imd.justforyou.databaseconfig.enumerate;

import lombok.Getter;

public enum EmotionCode {
    CALM(0, "平靜"), HAPPY(1, "快樂"), ECSTASY(2, "狂喜"), FRIENDLINESS(3, "友愛"), ACCEPT(4, "接受"), TRUST(5, "信任"), RESPECT(6, "崇敬"), YIELD(7, "屈服"), WORRY(8, "擔心"), FEAR(9, "恐懼"), THRILLER(10, "驚悚"), AWE(11, "敬畏"), PUZZLED(12, "不解"), SURPRISE(13, "驚訝"), CONSTERNATION(14, "驚愕"), CONTRA(15, "反對"), SAD(16, "傷感"), AFFLICTED(17, "悲傷"), GRIEF(18, "悲痛"), REGRET(19, "懊悔"), BORED(20, "厭倦"), DISGUST(21, "厭惡"), HATRED(22, "憎恨"), DISDAIN(23, "鄙夷"), IMPATIENT(24, "不耐煩"), ANGRY(25, "生氣"), FURY(26, "暴怒"), PROVOCATIVE(27, "挑釁"), CARE(28, "關心"), EXPECT(29, "期待"), ALERT(30, "警惕"), OPTIMISM(31, "樂觀");

    @Getter
    private final Integer value;
    @Getter
    private final String description;

    EmotionCode(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Integer of(String description) {
        for (EmotionCode emotionCode : EmotionCode.values()) {
            if (emotionCode.getDescription().equals(description)) {
                return emotionCode.getValue();
            }
        }
        return null;
    }
}