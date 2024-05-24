package com.taskify.models;

import com.taskify.app.AppConstants;
import com.taskify.models.templates.FunctionMetadataTemplateModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = AppConstants.META_FIELD_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MetaFieldModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String fieldName;

    private String fieldValue;

    @ManyToOne(targetEntity = FunctionMetadataModel.class)
    private FunctionMetadataModel functionMetadataModel;

}
