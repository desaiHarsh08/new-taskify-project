package com.taskify.models;

import com.taskify.app.AppConstants;
import com.taskify.app.AppRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = AppConstants.ROLES_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String role = AppRole.SALES.name();

    private Integer userId;

}
