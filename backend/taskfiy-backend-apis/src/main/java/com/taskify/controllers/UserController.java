package com.taskify.controllers;

import com.taskify.dtos.UserDto;
import com.taskify.models.UserModel;
import com.taskify.services.UserServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/v1/users")
    public ApiResponse fetchAllUsers() {
        List<UserDto> userDtoList = this.userServices.fetchAllUsers();
        ApiResponse apiResponse = new ApiResponse();
        if(userDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO USER EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL USERS!");
        }
        apiResponse.setPayload(userDtoList);

        return apiResponse;
    }

    @GetMapping("/v1/users/{userId}")
    public ApiResponse fetchById(@PathVariable Integer userId) {
        UserDto userDto = this.userServices.fetchById(userId);

        ApiResponse apiResponse = new ApiResponse();
        if(userDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO USER EXIST WITH USER_ID: " + userId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("USER FOUND!");
        }
        apiResponse.setPayload(userDto);

        return apiResponse;
    }

    @GetMapping("/v1/users/email/{email}")
    public ApiResponse fetchByEmail(@PathVariable String email) {
        UserDto userDto = this.userServices.fetchByEmail(email);

        ApiResponse apiResponse = new ApiResponse();
        if(userDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO USER EXIST WITH EMAIL: " + email);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("USER FOUND!");
        }
        apiResponse.setPayload(userDto);

        return apiResponse;
    }

    @PutMapping("/v1/users/{userId}")
    public ApiResponse updateUser(@RequestBody UserModel userModel, @PathVariable Integer userId) {
        UserDto userDto = this.userServices.updateUser(userModel, userId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(404);
        apiResponse.setHttpStatus(HttpStatus.OK);
        if(userDto == null) {
            apiResponse.setMessage("NO USER EXIST WITH USER_ID: " + userId);
        }
        else {
            apiResponse.setMessage("USER FOUND!");
        }
        apiResponse.setPayload(userDto);

        return apiResponse;
    }

    @DeleteMapping("/v1/users/{userId}")
    public ApiResponse deleteUser(@PathVariable Integer userId) {
        Integer deletedUserId = this.userServices.deleteUser(userId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(404);
        apiResponse.setHttpStatus(HttpStatus.OK);
        if(deletedUserId == -1) {
            apiResponse.setMessage("NO USER EXIST WITH USER_ID: " + userId);
        }
        else {
            apiResponse.setMessage("USER DELETED!");
        }
        apiResponse.setPayload(deletedUserId);

        return apiResponse;
    }

}
