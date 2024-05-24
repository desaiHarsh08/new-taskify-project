package com.taskify.dtos;

import com.taskify.app.AppDepartment;
import com.taskify.models.TaskModel;
import com.taskify.models.UserModel;
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
public class FunctionDto {

    private Integer id;

    private String functionTitle;

    private String functionDescription;

    private String functionDepartment = AppDepartment.QUOTATION.name();

    private Date functionCreatedDate;

    private Integer functionCreatedByUserId;

    private Integer functionAssignedToUserId;

    private boolean isFunctionDone;

    private Date functionFinishedDate;

    private Integer functionFinishedByUserId;

    private boolean isDefaultFunction;

    private Integer taskModelId;

    private List<FunctionMetadataDto> functionMetadataDtoList;

}
