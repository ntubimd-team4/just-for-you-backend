package tw.edu.ntub.imd.justforyou.util.http;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import tw.edu.ntub.imd.justforyou.exception.form.InvalidFormException;

@UtilityClass
public class BindingResultUtils {
    public void validate(BindingResult bindingResult) throws InvalidFormException {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            System.out.println(bindingResult.getTarget());
            throw new InvalidFormException(bindingResult.getFieldError());
        }
    }
}
