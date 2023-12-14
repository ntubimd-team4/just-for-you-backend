package tw.edu.ntub.imd.justforyou.util.data;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.view.RecommendRecord;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.util.json.object.CollectionObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

import java.util.List;

@UtilityClass
public class ObjectDataUtils {
    public void addMusicListToObjectData(ObjectData objectData, List<RecommendRecord> list) {
        CollectionObjectData data = objectData.createCollectionData();
        data.add("musicList", list,
                (contentData, content) -> {
                    contentData.add("rid", content.getRid());
                    contentData.add("song", content.getSong());
                    contentData.add("thumbnails", content.getThumbnails());
                    contentData.add("link", content.getLink());
                    contentData.add("emotion_tag", content.getEmotionTag());
                    contentData.add("description", EmotionCode.convertToDescription(content.getEmotionTag()));
                    contentData.add("isCollection", content.getCollection());
                });
    }
}