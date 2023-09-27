package tw.edu.ntub.imd.justforyou.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.justforyou.bean.MusicBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.MusicEmotionDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.Music;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.MusicEmotion;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.EmotionCode;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.MusicService;
import tw.edu.ntub.imd.justforyou.service.transformer.MusicTransformer;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MusicServiceImpl extends BaseServiceImpl<MusicBean, Music, Integer> implements MusicService {
    @Value("${server.emotion-token.value}")
    private String emotionToken;
    @Value("${server.youtube-api-key.value}")
    private String apiKey;

    private final String apiUrl = "https://www.googleapis.com/youtube/v3/search?key=" + apiKey + "&maxResults=1&part=snippet&q=";

    private final MusicDAO musicDAO;
    private final MusicEmotionDAO musicEmotionDAO;
    private final MusicTransformer musicTransformer;

    public MusicServiceImpl(MusicDAO musicDAO, MusicEmotionDAO musicEmotionDAO, MusicTransformer musicTransformer) {
        super(musicDAO, musicTransformer);
        this.musicDAO = musicDAO;
        this.musicEmotionDAO = musicEmotionDAO;
        this.musicTransformer = musicTransformer;
    }

    @Override
    public MusicBean save(MusicBean musicBean) {
        return null;
    }

    private String[] searchYoutube(String songName) {
        String[] result = new String[2];

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + songName;
        String js = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(js);
            JsonNode items = jsonNode.get("items");
            for (JsonNode item : items) {
                result[0] = item.get("id").get("videoId").asText();
                result[1] = item.get("snippet").get("thumbnails").get("high").get("url").asText();
                return result;
            }
        } catch (Exception e) {
            System.out.println("showYoutubeErrorMsg： " + e.getMessage());
        }
        return null;
    }

    @Override
    public void searchMusic(MultipartFile file) {
        OpenAiService service = new OpenAiService(emotionToken, Duration.ofSeconds(60));
        String emotionText;
        try {
            List<Music> musicList = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
                if (index > 0) {
                    Music musicExcel = new Music();

                    XSSFRow row = worksheet.getRow(index);
                    musicExcel.setSong(row.getCell(0).getStringCellValue());

                    musicList.add(musicExcel);
                }
            }

            for (Music musicText : musicList) {
                try {
                    emotionText = service.createCompletion(emotionRequest(musicText.getSong())).getChoices().get(0).getText();

                    String[] emotions = {"平靜", "快樂", "狂喜", "友愛", "接受", "信任", "崇敬", "屈服", "擔心", "恐懼",
                            "驚悚", "敬畏", "不解", "驚訝", "驚愕", "反對", "傷感", "悲傷", "悲痛", "懊悔", "厭倦", "厭惡",
                            "憎恨", "鄙夷", "不耐煩", "生氣", "暴怒", "挑釁", "關心", "期待", "警惕", "樂觀"};
                    List<String> emotionList = new ArrayList<>();
                    for (String emotion : emotions) {
                        if (emotionText.contains(emotion)) {
                            emotionList.add(emotion);
                        }
                    }

                    Music music = new Music();
                    music.setSong(musicText.getSong());
                    music.setLink("https://www.youtube.com/watch?v=" + Objects.requireNonNull(searchYoutube(musicText.getSong()))[0]);
                    music.setThumbnails(Objects.requireNonNull(searchYoutube(musicText.getSong()))[1]);
                    Music music1 = musicDAO.save(music);

                    for (String emotionStr : emotionList) {
                        MusicEmotion musicEmotion = new MusicEmotion();
                        musicEmotion.setMid(music1.getMid());
                        musicEmotion.setEmotionTag(EmotionCode.convertToValue(emotionStr));
                        musicEmotionDAO.save(musicEmotion);
                    }
                } catch (Exception e) {
                    throw new NotFoundException("請重新發送請求" + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    private CompletionRequest emotionRequest(String prompt) {
        String mood = "平靜、快樂、狂喜、友愛、接受、信任、崇敬、屈服、擔心、恐懼、驚悚、敬畏、不解、驚訝、驚愕、" +
                "反對、傷感、悲傷、悲痛、懊悔、厭倦、厭惡、憎恨、鄙夷、不耐煩、生氣、暴怒、挑釁、關心、期待、警惕、樂觀";
        return CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("請問 " + prompt + " 這首歌在 " + mood + " 以上這些情緒中帶有哪5種主要情緒")
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
    }
}