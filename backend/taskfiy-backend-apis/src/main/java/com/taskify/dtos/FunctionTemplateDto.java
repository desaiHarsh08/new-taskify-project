package com.taskify.dtos;

import com.taskify.app.AppDepartment;
import com.taskify.models.templates.TaskTemplateModel;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionTemplateDto {

    private Integer id;

    private String functionTitle;

    private String functionDescription;

    private String functionDepartment = AppDepartment.QUOTATION.name();

    private boolean isDefaultFunction;

    private Integer taskTemplateModelId;

    private List<FunctionMetadataTemplateDto> functionMetadataTemplateDtos;

}
