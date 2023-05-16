package tw.edu.ntub.imd.justforyou.dto;

import tw.edu.ntub.imd.justforyou.dto.file.directory.Directory;

import java.nio.file.StandardCopyOption;

public interface Copyable {
    void copyTo(Directory newDirectory, StandardCopyOption... options);
}
