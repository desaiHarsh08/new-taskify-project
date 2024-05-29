package com.taskify.models.templates;

import com.taskify.app.AppConstants;
import com.taskify.app.AppDepartment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = AppConstants.FUNCTION_TEMPLATE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionTemplateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String functionTitle;

    private String functionDescription;

    private String functionDepartment = AppDepartment.QUOTATION.name();

    private boolean isDefaultFunction;

    @ManyToOne(targetEntity = TaskTemplateModel.class)
    private TaskTemplateModel taskTemplateModel;

}
