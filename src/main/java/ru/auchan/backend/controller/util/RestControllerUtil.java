package ru.auchan.backend.controller.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;

public final class RestControllerUtil {
  private RestControllerUtil() {

  }

  public static Map<String, Object> createResponseMap(final Page dataPage) {
    final Map<String, Object> response = new HashMap<>();
    response.put("data", dataPage.getContent());
    response.put("currentPage", dataPage.getNumber());
    response.put("totalItems", dataPage.getTotalElements());
    response.put("totalPages", dataPage.getTotalPages());
    return response;
  }
}
