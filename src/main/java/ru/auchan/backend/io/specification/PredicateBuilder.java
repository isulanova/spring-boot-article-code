package ru.auchan.backend.io.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PredicateBuilder<T> {

  private List<Predicate> predicates;

  private CriteriaBuilder builder;

  private Root<T> entityRoot;

  private ZoneOffset tz;
}
