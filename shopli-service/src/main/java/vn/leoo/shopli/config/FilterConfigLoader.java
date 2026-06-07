package vn.leoo.shopli.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import vn.leoo.shopli.dto.dlsfilter.FieldConfig;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FilterConfigLoader {

    private final Map<String, FieldConfig> configs;

    public FilterConfigLoader(ObjectMapper mapper)
            throws Exception {

        InputStream is =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                "ConfigureDynamicFilters.json");

        List<FieldConfig> lst =
                mapper.readValue(
                        is,
                        new TypeReference<>() {
                        }
                );

        configs =
                lst.stream()
                        .collect(
                                Collectors.toMap(
                                        FieldConfig::getField,
                                        Function.identity()
                                )
                        );
    }

    public FieldConfig get(String field) {

        return configs.get(field);

    }

}