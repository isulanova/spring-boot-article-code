package ru.auchan.backend.io.specification;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZoneOffset;
import java.util.List;

@Builder
@Getter
public class PredicateBuilder<T> {

  private List<Predicate> predicates;

  private CriteriaBuilder builder;

  private Root<T> entityRoot;

  private ZoneOffset tz;
}
