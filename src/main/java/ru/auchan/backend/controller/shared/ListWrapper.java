package ru.auchan.backend.controller.shared;

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
