package com.taskify.services;

import com.taskify.dtos.UserDto;
import com.taskify.models.RoleModel;
import com.taskify.models.UserModel;

import java.util.List;

public interface UserServices {

    public UserDto createUser(UserModel userModel);

    public List<UserDto> fetchAllUsers();

    public List<UserDto> fetchByDepartment(String department);

    public UserDto fetchById(Integer userId);

    public UserDto fetchByEmail(String email);

    public UserDto updateUser(UserModel updatedUserModel, Integer userId);

    public Integer deleteUser(Integer userId);

}
