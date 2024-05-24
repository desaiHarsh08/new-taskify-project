package com.taskify.services.impl;

import com.taskify.models.RoleModel;
import com.taskify.repositories.RoleRepository;
import com.taskify.services.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServicesImpl implements RoleServices {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public RoleModel createRole(RoleModel roleModel) {
        List<RoleModel> roleModelList = this.roleRepository.findByUserId(roleModel.getUserId());
        if(roleModelList != null) { // Role exist
            for (RoleModel model : roleModelList) {
                if (model.getRole().equalsIgnoreCase(roleModel.getRole())) {
                    return null; // Role already added
                }
            }
            return this.roleRepository.save(roleModel);
        }
        return this.roleRepository.save(roleModel);
    }

    @Override
    public List<RoleModel> fetchAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public List<RoleModel> fetchRolesByUserId(Integer userId) {
        return this.roleRepository.findByUserId(userId);
    }

    @Override
    public RoleModel updateRole(RoleModel updaRoleModel) {
        if(this.roleRepository.findById(updaRoleModel.getId()).isEmpty()) {
            return null;
        }
        return this.roleRepository.save(updaRoleModel);
    }

    @Override
    public Integer deleteRole(Integer roleId) {
        if(this.roleRepository.findById(roleId).isEmpty()) {
            return -1;
        }
        this.roleRepository.deleteById(roleId);
        return roleId;
    }

}
