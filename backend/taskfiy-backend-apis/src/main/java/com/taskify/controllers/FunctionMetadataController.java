package com.taskify.controllers;

import com.taskify.dtos.FunctionDto;
import com.taskify.dtos.FunctionMetadataDto;
import com.taskify.models.FunctionMetadataModel;
import com.taskify.services.FunctionMetadataServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

class MarkDoneRequest {
    public Integer finishedMetadataByUserId;
    public Date finishedDate;
}

class ForwardRequest {
    public Integer assignedUserId;
    public Integer functionMetadataModelId;
}

@RestController
@RequestMapping("/api/v1/function-metadatas")
public class FunctionMetadataController {
    
    @Autowired
    private FunctionMetadataServices functionMetadataServices;

    @PostMapping("/create")
    public ApiResponse createFunctionMetadataTemplateModel(@RequestBody FunctionMetadataDto functionMetadataDto) {
        FunctionMetadataDto createdFunctionMetadataDto = this.functionMetadataServices.createFunctionMetadata(functionMetadataDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdFunctionMetadataDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("FUNCTION_METADATA ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW FUNCTION_METADATA CREATED!");
        }
        apiResponse.setPayload(createdFunctionMetadataDto);

        return apiResponse;
    }

    @PostMapping("/create-default")
    public ApiResponse createDefaultFunctionMetadata(@RequestBody FunctionMetadataDto functionMetadataDto) {
        FunctionMetadataDto createdFunctionMetadataDto = this.functionMetadataServices.createDefaultFunctionMetadata(functionMetadataDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdFunctionMetadataDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("FUNCTION_METADATA ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW FUNCTION_METADATA CREATED!");
        }
        apiResponse.setPayload(createdFunctionMetadataDto);

        return apiResponse;
    }

    @PostMapping("/forward-metadata")
    public ApiResponse forwardFunctionMetadataAndTask(@RequestBody ForwardRequest forwardRequest) {
        this.functionMetadataServices.forwardMetadata(forwardRequest.assignedUserId, forwardRequest.functionMetadataModelId);
        return new ApiResponse(
                200,
                HttpStatus.OK,
                "FORWARDED TO THE ASSIGNED USER!",
                null,
                true
        );
    }

    @GetMapping("")
    public ApiResponse fetchAllFunctionMetadataTemplates() {
        List<FunctionMetadataDto> functionMetadataDtoList = this.functionMetadataServices.fetchAllFunctionMetadata();

        ApiResponse apiResponse = new ApiResponse();
        if(functionMetadataDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_METADATA EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL FUNCTION_METADATA!");
        }
        apiResponse.setPayload(functionMetadataDtoList);

        return apiResponse;
    }

    @GetMapping("/{functionMetadataId}")
    public ApiResponse fetchById(@PathVariable Integer functionMetadataId) {
        FunctionMetadataDto functionMetadataDto = this.functionMetadataServices.fetchByFunctionMetadataId(functionMetadataId);

        ApiResponse apiResponse = new ApiResponse();
        if(functionMetadataDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO FUNCTION_METADATA EXIST WITH FUNCTION_METADATA_ID: " + functionMetadataId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("FUNCTION_METADATA FOUND!");
        }
        apiResponse.setPayload(functionMetadataDto);

        return apiResponse;
    }
    @PostMapping("/mark-done/{functionMetadataId}")
    public ApiResponse markFunctionMetadataDone(@PathVariable Integer functionMetadataId, @RequestBody MarkDoneRequest markDoneRequest) {
        System.out.println("api mark done");
        this.functionMetadataServices.markFunctionMetadataDone(functionMetadataId, markDoneRequest.finishedMetadataByUserId, markDoneRequest.finishedDate);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setMessage("FUNCTION_METADATA DONE!");
        apiResponse.setPayload(null);
        apiResponse.setSuccess(true);

        return apiResponse;
    }

    @PutMapping("/{functionMetadataId}")
    public ApiResponse updateFunctionMetadataTemplate(@RequestBody FunctionMetadataModel functionMetadataModel, @PathVariable Integer functionMetadataId) {
        FunctionMetadataDto updatedFunctionMetadataDto = this.functionMetadataServices.updateFunctionMetadata(functionMetadataModel);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedFunctionMetadataDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION_METADATA EXIST WITH FUNCTION_METADATA_ID: " + functionMetadataId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION_METADATA UPDATED!");
        }
        apiResponse.setPayload(updatedFunctionMetadataDto);

        return apiResponse;
    }

    @DeleteMapping("/{functionMetadataId}")
    public ApiResponse deleteFunctionMetadataTemplate(@PathVariable Integer functionMetadataId) {
        Integer deletedFunctionMetadataTemplateId = this.functionMetadataServices.deleteFunctionMetadata(functionMetadataId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedFunctionMetadataTemplateId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO FUNCTION_METADATA EXIST WITH FUNCTION_METADATA_ID: " + functionMetadataId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("FUNCTION_METADATA DELETED!");
        }
        apiResponse.setPayload(deletedFunctionMetadataTemplateId);

        return apiResponse;
    }
    
}
