package ru.auchan.backend.io.specification;

import static java.util.Objects.isNull;

import jakarta.persistence.criteria.Predicate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import ru.auchan.backend.controller.shared.request.TemplateRequestSearchFilters;
import ru.auchan.backend.io.entity.TemplateEntity;

public final class TemplateEntitySpecification {

  private TemplateEntitySpecification() {
    // new instance denied
  }

  public static Specification<TemplateEntity> getSpecification(
      final TemplateRequestSearchFilters filter) {

    return (root, query, builder) -> {
      final PredicateBuilder<TemplateEntity> predicateBuilder =
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
      final PredicateBuilder predicateBuilder, final TemplateRequestSearchFilters filter) {
    // some code here
  }
}
