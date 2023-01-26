package ru.auchan.backend.controller.shared.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageableResponse<T> {
  List<T> data;
  int currentPage;
  int pageSize;
  long totalItems;
  int totalPages;

  public PageableResponse() {}

  public PageableResponse(
      int currentPage, int pageSize, long totalItems, int totalPages, List<T> data) {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.totalItems = totalItems;
    this.totalPages = totalPages;
    this.data = data;
  }

  public PageableResponse(Page<T> page) {
    this.currentPage = page.getNumber();
    this.pageSize = page.getSize();
    this.totalItems = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.data = page.getContent();
  }
}
