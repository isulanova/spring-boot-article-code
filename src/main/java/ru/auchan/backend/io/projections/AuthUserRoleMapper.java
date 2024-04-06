package ru.auchan.backend.io.projections;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Collections;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.auchan.backend.controller.role.shared.response.RoleItemResponse;

@Slf4j
@Component
public class AuthUserRoleMapper extends BaseMapper<Set<RoleItemResponse>> {

  @Override
  public Set<RoleItemResponse> transform(final String data) {
    if (isNull(data) || data.contains("null")) {
      return Collections.emptySet();
    }
    try {
      return Set.of(getMapper().readValue(data, RoleItemResponse[].class));
    } catch (final JsonProcessingException e) {
      log.error("Exception while create supplier object from jsonb. Message: " + e.getMessage());
      return Collections.emptySet();
    }
  }
}
