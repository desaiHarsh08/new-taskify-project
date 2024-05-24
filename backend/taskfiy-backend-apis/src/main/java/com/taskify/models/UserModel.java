package com.taskify.models;

import com.taskify.app.AppConstants;
import com.taskify.app.AppDepartment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = AppConstants.USERS_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private String email;

    private String password;


    private  String department = AppDepartment.QUOTATION.name();

    @OneToMany(targetEntity = RoleModel.class)
    @JoinColumn(name = "role_id_fk")
    private List<RoleModel> roles;

    private boolean isDisabled;



}
