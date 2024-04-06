package ru.auchan.backend.io.projections;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseMapper<T> {

  public abstract T transform(String data);

  public ObjectMapper getMapper() {
    return ObjectMapperUtil.getObjectMapperForServices();
  }
}
