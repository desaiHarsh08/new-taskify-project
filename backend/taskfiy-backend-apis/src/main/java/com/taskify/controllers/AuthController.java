package com.taskify.controllers;

import com.taskify.dtos.UserDto;
import com.taskify.models.RefreshTokenModel;
import com.taskify.models.UserModel;
import com.taskify.security.JwtTokenHelper;
import com.taskify.services.RefreshTokenServices;
import com.taskify.services.UserServices;
import com.taskify.utils.ApiResponse;
import com.taskify.utils.AuthPayload;
import com.taskify.utils.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserServices userServices;

    @Autowired
    private RefreshTokenServices refreshTokenServices;

    @PostMapping("/create")
    public ApiResponse createUser(@RequestBody UserModel userModel) {
        System.out.println(userModel.getEmail());
        UserDto userDto = this.userServices.createUser(userModel);
        ApiResponse apiResponse = new ApiResponse();
        if(userDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setMessage("USER ALREADY EXIST!");
            apiResponse.setSuccess(false);
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setMessage("USER CREATED!");
            apiResponse.setSuccess(true);
        }
        apiResponse.setPayload(userDto);

        return apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse doLogin(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest);
        this.authenticateUser(authRequest.getEmail(), authRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getEmail());

        String token = this.jwtTokenHelper.generateToken(userDetails);
        AuthPayload authPayload = new AuthPayload(
                token, this.refreshTokenServices.createRefreshToken(authRequest.getEmail()).getRefreshToken()
        );

        return new ApiResponse(200, HttpStatus.OK, "LOGGED IN!", authPayload, true);
    }

    private void authenticateUser(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        this.authenticationManager.authenticate(authenticationToken);
    }

    @PostMapping("/refresh-token")
    public ApiResponse refreshToken(@RequestBody Map<String, String> json) {
        RefreshTokenModel refreshTokenModel = this.refreshTokenServices.verifyRefreshToken(json.get("refreshToken"));

        UserModel userModel = refreshTokenModel.getUserModel();

        String token = this.jwtTokenHelper.generateToken(this.userDetailsService.loadUserByUsername(userModel.getEmail()));

        AuthPayload authPayload = new AuthPayload(token, refreshTokenModel.getRefreshToken());

        return new ApiResponse(200, HttpStatus.OK, "REFRESHED TOKEN!", authPayload, true);
    }

    @GetMapping("/test")
    public Object testApi() {
        System.out.println("Test api");
        return null;
    }

}
