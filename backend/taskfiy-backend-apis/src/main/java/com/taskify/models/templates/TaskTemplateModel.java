package com.taskify.models.templates;

import com.taskify.app.AppConstants;
import com.taskify.models.UserModel;
import com.taskify.models.templates.FunctionTemplateModel;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = AppConstants.TASK_TEMPLATE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskTemplateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

}
