package ru.auchan.backend.io.specification;

import java.time.ZoneOffset;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
