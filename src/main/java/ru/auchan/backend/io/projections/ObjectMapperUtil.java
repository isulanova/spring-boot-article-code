package ru.auchan.backend.io.projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public final class ObjectMapperUtil {

  private static final ObjectMapper OBJECT_MAPPER_FOR_SERVICES = new ObjectMapper();

  static {
    OBJECT_MAPPER_FOR_SERVICES.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  private ObjectMapperUtil() {
    // new instance denied
  }

  public static ObjectMapper getObjectMapperForServices() {
    return OBJECT_MAPPER_FOR_SERVICES;
  }

  /**
   * Метод копирующий значение полей объекта из текущего объекта в целевой за исключением не
   * проинициализированных полей
   *
   * @param src - текущий объект
   * @param target - целевой объект
   */
  public static void copyProperties(final Object src, final Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
  }

  /**
   * Метод получения списка пустых полей в объекте
   *
   * @param source - текущий объект
   * @return - массив имен не проинициализированных полей
   */
  private static String[] getNullPropertyNames(final Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    final java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    final Set<String> emptyNames = new HashSet<>();
    for (final java.beans.PropertyDescriptor pd : pds) {
      final Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }

    final var result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }
}
