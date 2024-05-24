package com.taskify.models;

import com.taskify.app.AppConstants;
import com.taskify.app.AppDepartment;
import com.taskify.models.templates.TaskTemplateModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.config.Task;

import java.util.Date;

@Entity
@Table(name = AppConstants.FUNCTION_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FunctionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String functionTitle;

    private String functionDescription;

    private String functionDepartment = AppDepartment.QUOTATION.name();

    private Date functionCreatedDate;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel functionCreatedByUser;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel functionAssignedToUser;

    private boolean isFunctionDone;

    private Date functionFinishedDate;

    @ManyToOne(targetEntity = UserModel.class)
    private UserModel functionFinishedByUser;

    private boolean isDefaultFunction;

    @ManyToOne(targetEntity = TaskModel.class)
    private TaskModel taskModel;

}
