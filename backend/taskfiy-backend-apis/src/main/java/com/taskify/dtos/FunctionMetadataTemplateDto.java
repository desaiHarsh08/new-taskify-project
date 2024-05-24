package com.taskify.dtos;

import com.taskify.models.templates.FunctionTemplateModel;
import com.taskify.models.templates.MetaFieldTemplateModel;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionMetadataTemplateDto {

    private Integer id;

    private String metadataTitle;

    private String metadataDescription;

    private Integer functionTemplateModelId;

    private List<MetaFieldTemplateModel> metaFieldTemplateModelList;

}
