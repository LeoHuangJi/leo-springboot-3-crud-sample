package vn.leoo.shopli.dto.dlsfilter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.leoo.shopli.config.FilterConfigLoader;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DynamicFilterEngine {

    private final FilterConfigLoader loader;

    public BuildResult build(
            DynamicFilterRequest req) {

        StringBuilder where =
                new StringBuilder();

        Map<String, Object> params =
                new HashMap<>();

        int idx = 0;

        for (FilterCondition c :
                req.getConditions()) {

            FieldConfig field =
                    loader.get(
                            c.getField()
                    );

            if (field == null) {

                throw new RuntimeException(
                        "Invalid field"
                );

            }

            OperatorConfig op =
                    field.getOperators()
                            .stream()
                            .filter(
                                    x ->
                                            x.getKey()
                                                    .equals(
                                                            c.getOperator()
                                                    )
                            )
                            .findFirst()
                            .orElseThrow();

            String param =
                    "p" + idx++;

            String sql =
                    op.getSql()
                            .replace(
                                    "{column}",
                                    field.getDbColumn()
                            )
                            .replace(
                                    "{param}",
                                    param
                            );

            if (where.length() > 0) {

                where.append(" AND ");

            }

            where.append("(")
                    .append(sql)
                    .append(")");

            buildParam(
                    op,
                    param,
                    c.getValue(),
                    params
            );

        }

        return new BuildResult(
                where.toString(),
                params
        );

    }

    private void buildParam(
            OperatorConfig op,
            String param,
            Object value,
            Map<String, Object> params) {

        if (value == null) {

            return;

        }

        String transform =
                op.getTransform();

        Object v = value;

        if ("LIKE_BOTH".equals(
                transform)) {

            v = "%" + value + "%";

        }

        if ("LIKE_LEFT".equals(
                transform)) {

            v = "%" + value;

        }

        if ("LIKE_RIGHT".equals(
                transform)) {

            v = value + "%";

        }

        params.put(
                param,
                v
        );

    }

}