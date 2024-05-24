package com.taskify.models.templates;

import com.taskify.app.AppConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = AppConstants.FUNCTION_METADATA_TEMPLATE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionMetadataTemplateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String metadataTitle;

    private String metadataDescription;

    @ManyToOne(targetEntity = FunctionTemplateModel.class, cascade = CascadeType.ALL)
    private FunctionTemplateModel functionTemplateModel;

}
