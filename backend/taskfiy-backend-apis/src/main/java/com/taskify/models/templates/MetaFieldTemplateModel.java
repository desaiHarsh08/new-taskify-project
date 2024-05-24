package com.taskify.models.templates;

import com.taskify.app.AppConstants;
import com.taskify.models.templates.FunctionMetadataTemplateModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = AppConstants.METAFIELD_TEMPLATE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MetaFieldTemplateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String fieldName;

    private String fieldValue;

    @ManyToOne(targetEntity = FunctionMetadataTemplateModel.class, cascade = CascadeType.ALL)
    private FunctionMetadataTemplateModel functionMetadataTemplateModel;

}
