package tw.edu.ntub.imd.justforyou.service.impl;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.justforyou.bean.EmotionBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.EmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicEmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicRecommendDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Emotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicRecommend;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.EmotionService;
import tw.edu.ntub.imd.justforyou.service.transformer.EmotionTransformer;
import tw.edu.ntub.imd.justforyou.util.data.SymbolUtils;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmotionServiceImpl extends BaseServiceImpl<EmotionBean, Emotion, Integer> implements EmotionService {
    @Value("${server.text-token.value}")
    private String textToken;
    private final EmotionDAO emotionDAO;
    private final EmotionTransformer emotionTransformer;
    private final MusicDAO musicDAO;
    private final MusicEmotionDAO musicEmotionDAO;
    private final MusicRecommendDAO musicRecommendDAO;

    public EmotionServiceImpl(EmotionDAO emotionDAO,
                              EmotionTransformer emotionTransformer,
                              MusicDAO musicDAO,
                              MusicEmotionDAO musicEmotionDAO,
                              MusicRecommendDAO musicRecommendDAO) {
        super(emotionDAO, emotionTransformer);
        this.emotionDAO = emotionDAO;
        this.emotionTransformer = emotionTransformer;
        this.musicDAO = musicDAO;
        this.musicEmotionDAO = musicEmotionDAO;
        this.musicRecommendDAO = musicRecommendDAO;
    }

    @Override
    public EmotionBean save(EmotionBean emotionBean) {
        return null;
    }

    @Override
    public List<MusicEmotion> searchMusic(Integer sid) {
        List<Integer> emotionList = emotionDAO.findBySid(sid);
        List<MusicEmotion> musicEmotionList = musicEmotionDAO.findByEmotionTagIn(emotionList);
        List<MusicEmotion> collect = musicEmotionList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(
                                MusicEmotion::getMid))), ArrayList::new));
        Collections.shuffle(collect);
        if (collect.size() > 10) {
            collect = collect.stream().distinct().collect(Collectors.toList()).subList(0, 10);
        }
        return collect;
    }

    @Override
    public String searchBySid(Integer sid) {
        List<Integer> emotionList = emotionDAO.findBySid(sid);
        List<String> list = new ArrayList<>();
        for (Integer emotion : emotionList) {
            list.add(EmotionCode.convertToDescription(emotion));
        }
        return SymbolUtils.remoteSymbol(list);
    }

    @Override
    public String generateText(List<MusicEmotion> musicEmotionList) {
        List<String> recommendMusicList = new ArrayList<>();
        for (MusicEmotion musicEmotion : musicEmotionList) {
            recommendMusicList.add(EmotionCode.convertToDescription(musicEmotion.getEmotionTag()));
        }
        List<String> list = recommendMusicList.stream().distinct().collect(Collectors.toList());

        OpenAiService service = new OpenAiService(textToken, Duration.ofSeconds(60));
        String emotionText;
        try {
            emotionText = service.createCompletion(textRequest(list.toString())).getChoices().get(0).getText();
        } catch (Exception e) {
            throw new NotFoundException("請重新發送請求");
        }

        return emotionText
                .replace("\n", "")
                .replace("語「", "")
                .replace("「", "")
                .replace("」", "");
    }

    private CompletionRequest textRequest(String prompt) {
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("生成一句根據" + prompt + "心情的正向激勵話")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
    }

    @Override
    public List<Music> recommendMusic(Integer sid, List<MusicEmotion> musicEmotionList) {
        List<Music> recommendMusicList = new ArrayList<>();
        for (MusicEmotion musicEmotion : musicEmotionList) {
            MusicRecommend musicRecommend = new MusicRecommend();
            musicRecommend.setSid(sid);
            musicRecommend.setMusicEmoId(musicEmotion.getId());
            musicRecommendDAO.save(musicRecommend);

            Music music = new Music();
            Optional<Music> musicOptional = musicDAO.findByMid(musicEmotion.getMid());
            if (musicOptional.isPresent()) {
                Music musicData = musicOptional.get();
                JavaBeanUtils.copy(musicData, music);
            }
            recommendMusicList.add(music);
        }
        return recommendMusicList;
    }
}