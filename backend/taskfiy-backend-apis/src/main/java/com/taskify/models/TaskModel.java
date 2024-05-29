package com.taskify.models;

import com.taskify.app.AppConstants;
import com.taskify.app.AppDepartment;
import com.taskify.app.TASK_PRIORITY;
import com.taskify.models.templates.TaskTemplateModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = AppConstants.TASK_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(targetEntity = TaskTemplateModel.class)
    private TaskTemplateModel taskTemplateModel;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel taskCreatedByUser;

    private Date taskCreatedDate;

    private String taskPriority = TASK_PRIORITY.NORMAL.name();

    private String taskAssignedToDepartment = AppDepartment.QUOTATION.name();

    private Date taskAssignedToDepartmentDate;

    private boolean isTaskCompleted;

    private Date taskFinishedDate;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel taskAssignToUser;

    private String pumpType;

    private String pumpManufacturer;

    private String specification;

    private String problemDescription;

    @ManyToOne(targetEntity = CustomerModel.class)
    private CustomerModel customerModel;


}
