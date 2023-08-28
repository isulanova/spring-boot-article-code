package ru.auchan.backend.io.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.model.constant.TemplateEnum;

@Getter
@Setter
@Table(name = "template_data")
@Entity
public class TemplateEntity extends BaseEntity implements Serializable {

  @Column(name = "string_type")
  private String stringType;

  @Enumerated(EnumType.STRING)
  @Column(name = "enum_type")
  private TemplateEnum enumType;

  @Column(name = "id_type")
  private UUID idType;

  @Column(name = "date_type")
  private LocalDateTime dateType;
}
