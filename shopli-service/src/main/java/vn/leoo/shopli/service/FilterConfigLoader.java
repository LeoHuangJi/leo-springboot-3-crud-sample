package vn.leoo.shopli.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import vn.leoo.shopli.dto.dlsfilter.FieldConfig;
import vn.leoo.shopli.dto.dlsfilter.res.FilterFieldConfig;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FilterConfigLoader {
    private final ObjectMapper mapper;
    private final Map<String, FieldConfig> configs;

    public FieldConfig get(String field) {

        return configs.get(field);

    }

    public FilterConfigLoader(ObjectMapper mapper)
            throws Exception {
        this.mapper = mapper;

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

    public Map<String, List<FilterFieldConfig>> loadAll() {

        try {

            Map<String,List<FilterFieldConfig>> result =
                    new HashMap<>();

            ResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

            Resource[] resources =
                    resolver.getResources(
                            "ConfigureDynamicFilters.json"
                    );

            for(Resource resource : resources){

                String fileName =
                        Objects.requireNonNull(
                                resource.getFilename()
                        );

                String module =
                        fileName.replace(
                                ".json",
                                ""
                        );

                List<FilterFieldConfig> config =
                        mapper.readValue(
                                resource.getInputStream(),
                                new TypeReference<
                                        List<FilterFieldConfig>
                                        >(){}
                        );

                result.put(
                        module,
                        config
                );

            }

            return result;

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Cannot load filter config",
                    ex
            );

        }

    }

}