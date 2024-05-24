package com.taskify.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskify.dtos.FunctionDto;
import com.taskify.dtos.FunctionDto;
import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.dtos.TaskDto;
import com.taskify.models.FunctionModel;
import com.taskify.services.FunctionServices;
import com.taskify.utils.ApiResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

class AddFunctionRequestBody {
    public FunctionDto functionDto;
    public FunctionTemplateDto functionTemplateDto;
}

class FunctionMarkDoneRequest {
    public Integer functionFinishedByUserId;
    public Date finishedDate;
}

@RestController
@RequestMapping("/api/v1/functions")
public class FunctionController {
    
    @Autowired
    private FunctionServices functionServices;

    @PostMapping("/create")
    public ApiResponse createFunction(@RequestBody AddFunctionRequestBody addFunctionRequestBody) {
        System.out.println(addFunctionRequestBody.functionDto);
        System.out.println(addFunctionRequestBody.getFunctionTemplateDto());
        // Convert the nested JSON objects into Java objects
        ObjectMapper mapper = new ObjectMapper();
        FunctionDto functionDto = addFunctionRequestBody.getFunctionDto();
        FunctionTemplateDto functionTemplateDto = addFunctionRequestBody.getFunctionTemplateDto();


        System.out.println("In controller,");
        System.out.println(functionTemplateDto);
        System.out.println(functionDto);
        System.out.println();
        FunctionDto createdFunctionDto = this.functionServices.createFunction(functionDto, functionTemplateDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdFunctionDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("FUNCTION ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW FUNCTION CREATED!");
        }
        apiResponse.setPayload(createdFunctionDto);

        return apiResponse;
    }

    @PostMapping("/create-default")
    public ApiResponse createDefaultFunction(@RequestBody AddFunctionRequestBody addFunctionRequestBody) {
        FunctionDto createdFunctionDto = this.functionServices.createDefaultFunction(addFunctionRequestBody.functionDto, addFunctionRequestBody.getFunctionTemplateDto());

        ApiResponse apiResponse = new ApiResponse();
        if(createdFunctionDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("FUNCTION ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW FUNCTION CREATED!");
        }
        apiResponse.setPayload(createdFunctionDto);

        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllFunction() {
        List<FunctionDto> FunctionDtoList = this.functionServices.fetchAllFunctions();

        ApiResponse apiResponse = new ApiResponse();
        if(FunctionDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL FUNCTIONS!");
        }
        apiResponse.setPayload(FunctionDtoList);

        return apiResponse;
    }

    @GetMapping("/by-task/{taskId}")
    public ApiResponse fetchByTaskId(@PathVariable Integer taskId) {
        List<FunctionDto> functionDtoList = this.functionServices.fetchByTaskId(taskId);

        ApiResponse apiResponse = new ApiResponse();
        if(functionDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION EXIST WITH TASK_ID: " + taskId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("FUNCTION FOUND!");
        }
        apiResponse.setPayload(functionDtoList);

        return apiResponse;

    }

    @GetMapping("/{functionId}")
    public ApiResponse fetchById(@PathVariable Integer functionId) {
        FunctionDto functionDto = this.functionServices.fetchByFunctionId(functionId);

        ApiResponse apiResponse = new ApiResponse();
        if(functionDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION EXIST WITH FUNCTION_ID: " + functionId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("FUNCTION FOUND!");
        }
        apiResponse.setPayload(functionDto);

        return apiResponse;
    }

    @PostMapping("/mark-done/{functionId}")
    public ApiResponse markFunctionDone(@PathVariable Integer functionId, @RequestBody FunctionMarkDoneRequest functionMarkDoneRequest) {
        this.functionServices.markFunctionDone(functionId, functionMarkDoneRequest.functionFinishedByUserId, functionMarkDoneRequest.finishedDate);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setMessage("FUNCTION DONE!");
        apiResponse.setPayload(null);

        return apiResponse;
    }

    @PutMapping("/{functionId}")
    public ApiResponse updateFunction(@RequestBody FunctionModel functionModel, @PathVariable Integer functionId) {
        FunctionDto updatedFunctionDto = this.functionServices.updateFunction(functionModel);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedFunctionDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION EXIST WITH FUNCTION_ID: " + functionId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION UPDATED!");
        }
        apiResponse.setPayload(updatedFunctionDto);

        return apiResponse;
    }

    @DeleteMapping("/{functionId}")
    public ApiResponse deleteFunction(@PathVariable Integer functionId) {
        Integer deletedFunctionId = this.functionServices.deleteFunction(functionId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedFunctionId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION EXIST WITH USER_ID: " + functionId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION DELETED!");
        }
        apiResponse.setPayload(deletedFunctionId);

        return apiResponse;
    }
    
}
