package vn.leoo.audit.log.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeepCopyUtil {

    private final ObjectMapper objectMapper;

    public <T> T copy(T source) {
        try {
            String json = objectMapper.writeValueAsString(source);
            return  objectMapper.readValue(json, (Class<T>)source.getClass());
        } catch (Exception e) {
            throw new RuntimeException("deepCopy thất bại: "
                    + source.getClass().getSimpleName(), e);
        }
    }

    public <T> List<T> copyList(List<T> source, Class<T> clazz) {
        try {
            String json = objectMapper.writeValueAsString(source);
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("copyList thất bại: " + clazz.getSimpleName(), e);
        }
    }
}