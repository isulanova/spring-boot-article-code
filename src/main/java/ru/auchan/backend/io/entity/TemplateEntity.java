package ru.auchan.backend.io.entity;

import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.model.constant.TemplateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
