package vn.leoo.audit.log.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListDiffUtil {

    private final DeepCopyUtil deepCopyUtil;

    /**
     * So sánh list cũ vs mới, phân loại INSERT / UPDATE / DELETE
     * @param oldList  list hiện tại trong DB
     * @param newList  list gửi xuống từ request
     * @param idExtractor hàm lấy id để so sánh
     */
    public <T> ListDiff<T> diff(List<T> oldList,
                                List<T> newList,
                                Function<T, String> idExtractor) {
        Map<String, T> oldMap = oldList.stream()
                .collect(Collectors.toMap(idExtractor, Function.identity()));

        Map<String, T> newMap = newList.stream()
                .collect(Collectors.toMap(idExtractor, Function.identity()));

        List<T> toInsert = newList.stream()
                .filter(n -> !oldMap.containsKey(idExtractor.apply(n)))
                .collect(Collectors.toList());

        List<T> toDelete = oldList.stream()
                .filter(o -> !newMap.containsKey(idExtractor.apply(o)))
                .collect(Collectors.toList());

        // UPDATE: có cả 2 phía
        List<UpdatePair<T>> toUpdate = newList.stream()
                .filter(n -> oldMap.containsKey(idExtractor.apply(n)))
                .map(n -> new UpdatePair<>(oldMap.get(idExtractor.apply(n)), n))
                .collect(Collectors.toList());

        return new ListDiff<>(toInsert, toUpdate, toDelete);
    }

    @Getter
    @AllArgsConstructor
    public static class ListDiff<T> {
        private final List<T> toInsert;
        private final List<UpdatePair<T>> toUpdate;
        private final List<T> toDelete;
    }

    @Getter
    @AllArgsConstructor
    public static class UpdatePair<T> {
        private final T oldObj;
        private final T newObj;
    }
}