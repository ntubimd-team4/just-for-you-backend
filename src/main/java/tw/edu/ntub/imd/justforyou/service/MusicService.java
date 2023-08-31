package tw.edu.ntub.imd.justforyou.service;

import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.justforyou.bean.MusicBean;

public interface MusicService extends BaseService<MusicBean, Integer> {
    void searchMusic(MultipartFile file);
}