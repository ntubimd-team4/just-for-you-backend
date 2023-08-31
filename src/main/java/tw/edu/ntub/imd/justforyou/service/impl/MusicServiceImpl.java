package tw.edu.ntub.imd.justforyou.service.impl;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

@Service
public class MusicServiceImpl extends BaseServiceImpl<MusicBean, Music, Integer> implements MusicService {
    @Value("${server.emotion-token.value}")
    private String emotionToken;

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

    @Override
    public void searchMusic(MultipartFile file) {
        getSeveralTracks_Sync(file);
    }

    public void getSeveralTracks_Sync(MultipartFile file) {
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
                    music.setLink("http://test");
                    Music music1 = musicDAO.save(music);

                    for (String emotionStr : emotionList) {
                        MusicEmotion musicEmotion = new MusicEmotion();
                        musicEmotion.setMid(music1.getMid());
                        musicEmotion.setEmotionTag(EmotionCode.of(emotionStr));
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