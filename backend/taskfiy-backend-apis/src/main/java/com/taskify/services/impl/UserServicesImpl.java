package com.taskify.services.impl;

import com.taskify.dtos.UserDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.RoleModel;
import com.taskify.models.UserModel;
import com.taskify.repositories.UserRepository;
import com.taskify.services.RoleServices;
import com.taskify.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServices roleServices;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserModel userModel) {
        System.out.println(userModel);
        if(this.userRepository.findByEmail(userModel.getEmail()) != null) {
            return null;
        }
        // Encrypt the raw password
        userModel.setPassword(this.bCryptPasswordEncoder.encode(userModel.getPassword()));
        // Create a user
        UserModel createdUserModel = this.userRepository.save(userModel);
        System.out.println("createdUserModel: " + createdUserModel);

        // Create the user's role
        for(RoleModel roleModel: userModel.getRoles()) {
            roleModel.setRole(roleModel.getRole());
            roleModel.setUserId(createdUserModel.getId());
            this.roleServices.createRole(roleModel);
        }

        return this.modelMapper.map(createdUserModel, UserDto.class);
    }

    @Override
    public List<UserDto> fetchAllUsers() {
        List<UserModel> userModelList = this.userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserModel userModel: userModelList) {
            userModel.setRoles(this.roleServices.fetchRolesByUserId(userModel.getId()));
            userDtoList.add(this.modelMapper.map(userModel, UserDto.class));
        }

        return  userDtoList;
    }

    @Override
    public List<UserDto> fetchByDepartment(String department) {
        List<UserModel> userModelList = this.userRepository.findByDepartment(department);
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserModel userModel: userModelList) {
            userModel.setRoles(this.roleServices.fetchRolesByUserId(userModel.getId()));
            userDtoList.add(this.modelMapper.map(userModel, UserDto.class));
        }

        return  userDtoList;
    }

    @Override
    public UserDto fetchById(Integer userId) {
        UserModel userModel = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        return this.modelMapper.map(userModel, UserDto.class);
    }

    @Override
    public UserDto fetchByEmail(String email) {
        UserModel userModel = this.userRepository.findByEmail(email);
        return this.modelMapper.map(userModel, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserModel updatedUserModel, Integer userId) {
        UserModel existingUser = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        existingUser.setName(updatedUserModel.getName());
        existingUser.setDisabled(updatedUserModel.isDisabled());
        existingUser.setDepartment(updatedUserModel.getDepartment());
        existingUser.setPassword(updatedUserModel.getPassword());
        this.userRepository.save(existingUser);
        return this.modelMapper.map(existingUser, UserDto.class);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        UserModel existingUser = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.deleteById(userId);
        return userId;
    }
}
