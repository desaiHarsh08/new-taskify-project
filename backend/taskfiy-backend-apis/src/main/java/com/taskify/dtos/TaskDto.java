package com.taskify.dtos;

import com.taskify.app.AppConstants;
import com.taskify.app.AppDepartment;
import com.taskify.app.TASK_PRIORITY;
import com.taskify.models.CustomerModel;
import com.taskify.models.UserModel;
import com.taskify.models.templates.TaskTemplateModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDto {

    private Integer id;

    private Integer taskTemplateModelId;

    private Integer taskCreatedByUserId;

    private Date taskCreatedDate;

    private String taskPriority = TASK_PRIORITY.NORMAL.name();

    private String taskAssignedToDepartment = AppDepartment.QUOTATION.name();

    private Date taskAssignedToDepartmentDate;

    private Integer taskAssignToUserId;

    private boolean isTaskCompleted;

    private Date taskFinishedDate;

    private Integer taskFinishedByUserId;

    private List<FunctionDto> functionDtoList;

    private String pumpType;

    private String pumpManufacturer;

    private String specification;

    private String problemDescription;

    private CustomerModel customerModel;

}
