package com.taskify.services;

import com.taskify.models.RoleModel;

import java.util.List;

public interface RoleServices {

    public RoleModel createRole(RoleModel roleModel);

    public List<RoleModel> fetchAllRoles();

    public List<RoleModel> fetchRolesByUserId(Integer userId);

    public RoleModel updateRole(RoleModel updaRoleModel);

    public Integer deleteRole(Integer roleId);

}
