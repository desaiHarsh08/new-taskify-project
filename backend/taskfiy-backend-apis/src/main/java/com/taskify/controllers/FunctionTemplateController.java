package com.taskify.controllers;


import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.models.templates.FunctionTemplateModel;
import com.taskify.services.FunctionTemplateServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/function-templates")
public class FunctionTemplateController {
    
    @Autowired
    private FunctionTemplateServices functionTemplateServices;

    @PostMapping("/create")
    public ApiResponse createFunctionTemplateModel(@RequestBody FunctionTemplateDto functionTemplateDto) {
        FunctionTemplateDto createdFunctionTemplateDto = this.functionTemplateServices.createFunctionTemplateModel(functionTemplateDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdFunctionTemplateDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("FUNCTION_TEMPLATE ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW FUNCTION_TEMPLATE CREATED!");
        }
        apiResponse.setPayload(createdFunctionTemplateDto);

        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllFunctionTemplates() {
        List<FunctionTemplateDto> functionTemplateDtoList = this.functionTemplateServices.fetchAllFunctionTemplate();

        ApiResponse apiResponse = new ApiResponse();
        if(functionTemplateDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_TEMPLATE EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL FUNCTION_TEMPLATES!");
        }
        apiResponse.setPayload(functionTemplateDtoList);

        return apiResponse;
    }

    @GetMapping("/task-template/{taskTemplateId}")
    public ApiResponse fetchAllFunctionTemplatesByTaskTemplateId(@PathVariable Integer taskTemplateId) {
        List<FunctionTemplateDto> functionTemplateDtoList = this.functionTemplateServices.fetchByTaskTemplateModelId(taskTemplateId);

        ApiResponse apiResponse = new ApiResponse();
        if(functionTemplateDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_TEMPLATE EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL FUNCTION_TEMPLATES!");
        }
        apiResponse.setPayload(functionTemplateDtoList);

        return apiResponse;
    }

    @GetMapping("/{functionTemplateId}")
    public ApiResponse fetchById(@PathVariable Integer functionTemplateId) {
        FunctionTemplateDto functionTemplateDto = this.functionTemplateServices.fetchById(functionTemplateId);

        ApiResponse apiResponse = new ApiResponse();
        if(functionTemplateDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_TEMPLATE EXIST WITH FUNCTION_TEMPLATE_ID: " + functionTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("FUNCTION_TEMPLATE FOUND!");
        }
        apiResponse.setPayload(functionTemplateDto);

        return apiResponse;
    }

    @PutMapping("/{functionTemplateId}")
    public ApiResponse updateFunctionTemplate(@RequestBody FunctionTemplateDto functionTemplateDto, @PathVariable Integer functionTemplateId) {
        FunctionTemplateDto updatedFunctionTemplateDto = this.functionTemplateServices.updateFunctionTemplate(functionTemplateDto, functionTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedFunctionTemplateDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION_TEMPLATE EXIST WITH Function_TEMPLATE_ID: " + functionTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION_TEMPLATE UPDATED!");
        }
        apiResponse.setPayload(updatedFunctionTemplateDto);

        return apiResponse;
    }

    @DeleteMapping("/{functionTemplateId}")
    public ApiResponse deleteFunctionTemplate(@PathVariable Integer functionTemplateId) {
        Integer deletedFunctionTemplateId = this.functionTemplateServices.deleteFunctionTemplate(functionTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedFunctionTemplateId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION_TEMPLATE EXIST WITH USER_ID: " + functionTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION_TEMPLATE DELETED!");
        }
        apiResponse.setPayload(deletedFunctionTemplateId);

        return apiResponse;
    }
    
}
