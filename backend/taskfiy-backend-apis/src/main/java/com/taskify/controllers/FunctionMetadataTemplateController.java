package com.taskify.controllers;

import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.models.templates.FunctionMetadataTemplateModel;
import com.taskify.services.FunctionMetadataTemplateServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/function-metadata-templates")
public class FunctionMetadataTemplateController {
    
    @Autowired
    private FunctionMetadataTemplateServices functionMetadataTemplateServices;

    @PostMapping("/create")
    public ApiResponse createFunctionMetadataTemplateModel(@RequestBody FunctionMetadataTemplateDto functionMetadataTemplateDto) {
        FunctionMetadataTemplateDto createdFunctionMetadataTemplateDto = this.functionMetadataTemplateServices.createFunctionMetadataTemplate(functionMetadataTemplateDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdFunctionMetadataTemplateDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("FUNCTION_METADATA_TEMPLATE ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW FUNCTION_METADATA_TEMPLATE CREATED!");
        }
        apiResponse.setPayload(createdFunctionMetadataTemplateDto);

        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllFunctionMetadataTemplates() {
        List<FunctionMetadataTemplateDto> functionMetadataTemplateDtoList = this.functionMetadataTemplateServices.fetchAllFunctionMetadataTemplates();

        ApiResponse apiResponse = new ApiResponse();
        if(functionMetadataTemplateDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_METADATA_TEMPLATE EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL FUNCTION_METADATA_TEMPLATES!");
        }
        apiResponse.setPayload(functionMetadataTemplateDtoList);

        return apiResponse;
    }

    @GetMapping("/{functionMetadataTemplateId}")
    public ApiResponse fetchById(@PathVariable Integer functionMetadataTemplateId) {
        FunctionMetadataTemplateDto functionMetadataTemplateDto = this.functionMetadataTemplateServices.fetchById(functionMetadataTemplateId);

        ApiResponse apiResponse = new ApiResponse();
        if(functionMetadataTemplateDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_METADATA_TEMPLATE EXIST WITH FUNCTION_METADATA_TEMPLATE_ID: " + functionMetadataTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("FUNCTION_METADATA_TEMPLATE FOUND!");
        }
        apiResponse.setPayload(functionMetadataTemplateDto);

        return apiResponse;
    }

    @PutMapping("/{functionMetadataTemplateId}")
    public ApiResponse updateFunctionMetadataTemplate(@RequestBody FunctionMetadataTemplateModel functionMetadataTemplateModel, @PathVariable Integer functionMetadataTemplateId) {
        FunctionMetadataTemplateDto updatedFunctionMetadataTemplateDto = this.functionMetadataTemplateServices.updateFunctionMetadata(functionMetadataTemplateModel, functionMetadataTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedFunctionMetadataTemplateDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION_METADATA_TEMPLATE EXIST WITH FUNCTION_METADATA_TEMPLATE_ID: " + functionMetadataTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION_METADATA_TEMPLATE UPDATED!");
        }
        apiResponse.setPayload(updatedFunctionMetadataTemplateDto);

        return apiResponse;
    }

    @DeleteMapping("/{functionMetadataTemplateId}")
    public ApiResponse deleteFunctionMetadataTemplate(@PathVariable Integer functionMetadataTemplateId) {
        Integer deletedFunctionMetadataTemplateId = this.functionMetadataTemplateServices.deleteFunctionMetadata(functionMetadataTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedFunctionMetadataTemplateId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION_METADATA_TEMPLATE EXIST WITH USER_ID: " + functionMetadataTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION_METADATA_TEMPLATE DELETED!");
        }
        apiResponse.setPayload(deletedFunctionMetadataTemplateId);

        return apiResponse;
    }
    
}
