package ru.auchan.backend.io.specification;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.auchan.backend.controller.shared.request.TemplateRequestSearchFilters;
import ru.auchan.backend.io.entity.TemplateEntity;

import javax.persistence.criteria.Predicate;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static java.util.Objects.isNull;

@NoArgsConstructor
public class TemplateEntitySpecification {

    public static Specification<TemplateEntity> getSpecification(
            TemplateRequestSearchFilters filter) {

        return (root, query, builder) -> {
            PredicateBuilder<TemplateEntity> predicateBuilder =
                    PredicateBuilder.<TemplateEntity>builder()
                            .entityRoot(root)
                            .predicates(new ArrayList<>())
                            .builder(builder)
                            .tz(ZoneOffset.of("+03:00"))
                            .build();

            if (!isNull(filter)) {
                fillPredicates(predicateBuilder, filter);
            }

            return builder.and(predicateBuilder.getPredicates().toArray(new Predicate[0]));
        };
    }

    private static void fillPredicates(
            PredicateBuilder predicateBuilder, TemplateRequestSearchFilters filter) {

    }
}
