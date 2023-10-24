package tw.edu.ntub.imd.justforyou.util.encryption;

import lombok.experimental.UtilityClass;
import org.jasypt.util.text.BasicTextEncryptor;

@UtilityClass
public class EncryptionUtils {
    public String cryptText(String text) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        setPassportKey(basicTextEncryptor);
        return basicTextEncryptor.encrypt(text);
    }

    public String decryptText(String text) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        setPassportKey(basicTextEncryptor);
        return basicTextEncryptor.decrypt(text);
    }

    private void setPassportKey(BasicTextEncryptor basicTextEncryptor) {
        basicTextEncryptor.setPassword("lybgeek");
    }
}