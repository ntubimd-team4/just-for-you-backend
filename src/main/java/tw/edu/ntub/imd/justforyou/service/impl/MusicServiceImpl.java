package tw.edu.ntub.imd.justforyou.service.impl;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetSeveralTracksRequest;
import tw.edu.ntub.imd.justforyou.bean.MusicBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.MusicService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicTransformer;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicServiceImpl extends BaseServiceImpl<MusicBean, Music, Integer> implements MusicService {
    @Value("${server.emotion-token.value}")
    private String emotionToken;

    // TODO 暫時的accessToken與ids，需更換
    private static final String accessToken = "BQALvY49CLHRROdIqB4o4QoP8rcBnnZbMefzyAHnBYSkGTVKaCR6yA_L98QVxLCex5AFCYBvEDgAwfKKk47QLBjfGM5hpQWcsUVTgnZU58CqFPSSJfNr8AdNvxghIxebEu24sY50bIO71nVEaA0SZG2g7MdfcGRRQVb62bdTzSpgT17elmTwwhTXu0mYdAWbS6t0mYV0UsMs3Wetoibt2YBaeRnjiY6ce8oLJFu4d-74MhiTAr-ZMDyJCs0XoGHXwQ";
    private static final String[] ids = new String[]{"01iyCAUm8EvOFqVWYJ3dVX", "7x9aauaA9cu6tyfpHnqDLo", "0ofHAoxe9vBkTCp2UQIavz"};
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetSeveralTracksRequest getSeveralTracksRequest = spotifyApi.getSeveralTracks(ids)
//          .market(CountryCode.SE)  //TODO 待研究
            .build();

    private final MusicDAO musicDAO;
    private final MusicTransformer musicTransformer;

    public MusicServiceImpl(MusicDAO musicDAO, MusicTransformer musicTransformer) {
        super(musicDAO, musicTransformer);
        this.musicDAO = musicDAO;
        this.musicTransformer = musicTransformer;
    }

    @Override
    public MusicBean save(MusicBean musicBean) {
        return null;
    }

    @Override
    public void searchMusic() {
        getSeveralTracks_Sync();
    }

    public void getSeveralTracks_Sync() {
        OpenAiService service = new OpenAiService(emotionToken, Duration.ofSeconds(60));
        String emotionText;
        try {
            final Track[] tracks = getSeveralTracksRequest.execute();
            for (Track track : tracks) {
                try {
                    emotionText = service.createCompletion(emotionRequest(track.getName())).getChoices().get(0).getText();

                    String[] emotions = {"平靜", "快樂", "狂喜", "友愛", "接受", "信任", "崇敬", "屈服", "擔心", "恐懼",
                            "驚悚", "敬畏", "不解", "驚訝", "驚愕", "反對", "傷感", "悲傷", "悲痛", "懊悔", "厭倦", "厭惡",
                            "憎恨", "鄙夷", "不耐煩", "生氣", "暴怒", "挑釁", "關心", "期待", "警惕", "樂觀"};
                    List<String> emotionList = new ArrayList<>();
                    for (String emotion : emotions) {
                        if (emotionText.contains(emotion)) {
                            emotionList.add(emotion);
                        }
                    }

                    for (String emotionStr : emotionList) {
                        Music music = new Music();
                        music.setSong(track.getName());
                        music.setSinger(track.getArtists()[0].getName());
                        music.setEmotionTag(EmotionCode.of(emotionStr));
                        musicDAO.save(music);
                    }
                } catch (Exception e) {
                    throw new NotFoundException("請重新發送請求" + e.getMessage());
                }
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    private CompletionRequest emotionRequest(String prompt) {
        String mood = "平靜、快樂、狂喜、友愛、接受、信任、崇敬、屈服、擔心、恐懼、驚悚、敬畏、不解、驚訝、驚愕、" +
                "反對、傷感、悲傷、悲痛、懊悔、厭倦、厭惡、憎恨、鄙夷、不耐煩、生氣、暴怒、挑釁、關心、期待、警惕、樂觀";
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("請問" + prompt + "在" + mood + "以下這些情緒中帶有哪5個主要情緒")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
    }
}