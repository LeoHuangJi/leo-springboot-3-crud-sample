package vn.leoo.common.properties;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class MessageService {
    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String resolve(int appCode) {
        return messageSource.getMessage(String.format("%04d", appCode), null, "Lỗi không xác định", Locale.getDefault());
    }
}