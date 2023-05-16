package tw.edu.ntub.imd.justforyou.exception.file;

public class EmptyFileException extends FileException {
    public EmptyFileException(String fileName) {
        super("未選擇檔案: " + fileName);
    }

    @Override
    public String getReason() {
        return "NotChoose";
    }
}
