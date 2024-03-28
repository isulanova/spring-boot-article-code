package ru.auchan.backend.controller.util;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListWrapper<T> {

  List<T> payload;

  public ListWrapper() {
    // Do nothing
  }
}
