package ru.auchan.backend.config.openapi;

public enum DefinitionStatus {
  OK_DEF("Success"),
  CREATED_DEF("Resource created successfully"),
  ACCEPTED_DEF("Resource updated successfully"),
  NO_CONTENT_DEF("Resource removed successfully"),
  BAD_REQUEST_DEF("Invalid request"),
  UNAUTHORIZED_DEF("Authorization failed"),
  NOT_FOUND_DEF("Resource not found"),
  INTERNAL_SERVER_ERROR_DEF("Server error");

  final String status;

  public String getStatus() {
    return status;
  }

  DefinitionStatus(String status) {
    this.status = status;
  }
}
