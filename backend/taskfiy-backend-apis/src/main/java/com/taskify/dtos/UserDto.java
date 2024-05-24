package com.taskify.dtos;

import com.taskify.app.AppDepartment;
import com.taskify.models.RoleModel;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private Integer id;

    private String name;

    private String email;

    private  String department = AppDepartment.QUOTATION.name();

    private List<RoleModel> roles;

    private boolean isDisabled;

}
