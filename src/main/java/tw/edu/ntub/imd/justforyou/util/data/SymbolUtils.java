package tw.edu.ntub.imd.justforyou.util.data;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@UtilityClass
public class SymbolUtils {
    public String remoteSymbol(List<String> list) {
        String remoteStart = StringUtils.removeStart(list.toString(), "[");
        return StringUtils.removeEnd(remoteStart, "]");
    }
}