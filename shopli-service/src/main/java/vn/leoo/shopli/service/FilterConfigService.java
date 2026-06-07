package vn.leoo.shopli.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vn.leoo.shopli.dto.dlsfilter.res.FilterFieldConfig;
import vn.leoo.shopli.dto.dlsfilter.res.FilterFieldResponse;
import vn.leoo.shopli.dto.dlsfilter.res.FilterOperatorResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterConfigService {

    private final FilterConfigLoader loader;

    public Map<String, List<FilterFieldResponse>>
    getAll(){

        return loader.loadAll()
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                x ->
                                        x.getValue()
                                                .stream()
                                                .map(this::convert)
                                                .toList()
                        )
                );

    }

    private FilterFieldResponse convert(
            FilterFieldConfig x
    ){

        FilterFieldResponse dto =
                new FilterFieldResponse();

        BeanUtils.copyProperties(
                x,
                dto,
                "dbColumn",
                "operators"
        );

        dto.setOperators(
                x.getOperators()
                        .stream()
                        .map(op -> {

                            FilterOperatorResponse r =
                                    new FilterOperatorResponse();

                            r.setKey(op.getKey());

                            r.setLabel(op.getLabel());

                            r.setMultiValue(
                                    op.getMultiValue()
                            );

                            return r;

                        })
                        .toList()
        );

        return dto;

    }

}