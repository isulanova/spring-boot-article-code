package ru.auchan.backend.io.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter
public class EntityChangeEventFields {

  @CreatedDate
  @Column(name = "record_created")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Moscow")
  private Date created;

  @LastModifiedDate
  @Column(name = "record_updated")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Moscow")
  private Date updated;

  @Enumerated(EnumType.STRING)
  @Column(name = "record_status")
  private DbRowStatus status;

  @PrePersist
  void onCreate() {
    this.created = new Date();
    this.updated = new Date();
    this.status = DbRowStatus.ACTIVE;
  }

  @PreUpdate
  void onPersist() {
    this.updated = new Date();
  }
}
