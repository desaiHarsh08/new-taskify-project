package com.taskify.dtos;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskTemplateDto {

    private Integer id;

    private List<FunctionTemplateDto> functionTemplateDtoList;

}
