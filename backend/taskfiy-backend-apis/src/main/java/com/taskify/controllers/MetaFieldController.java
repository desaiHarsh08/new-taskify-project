package com.taskify.controllers;

import com.taskify.dtos.FunctionMetadataDto;
import com.taskify.dtos.FunctionMetadataTemplateDto;
import com.taskify.dtos.MetaFieldDto;
import com.taskify.models.FunctionMetadataModel;
import com.taskify.models.MetaFieldModel;
import com.taskify.services.MetaFieldServices;
import com.taskify.utils.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
class MetaFieldDefaultRequest {
    public Integer functionMetadataModelId;
    public MetaFieldDto metaFieldDto;
}

@RestController
@RequestMapping("/api/v1/meta-fields")
public class MetaFieldController {
    
    @Autowired
    private MetaFieldServices metaFieldServices;

    @PostMapping("/create")
    public ApiResponse createMetaField(@RequestBody FunctionMetadataModel functionMetadataModel, FunctionMetadataTemplateDto functionMetadataTemplateDto) {
        List<MetaFieldModel> createdMetaFieldList = this.metaFieldServices.createMetaField(functionMetadataTemplateDto, functionMetadataModel);

        ApiResponse apiResponse = new ApiResponse();
        if(createdMetaFieldList == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("META_FIELD ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW META_FIELD CREATED!");
        }
        apiResponse.setPayload(createdMetaFieldList);

        return apiResponse;
    }

    @PostMapping("/create-default")
    public ApiResponse createDefaultMetaField(@RequestBody MetaFieldDefaultRequest metaFieldDefaultRequest) {
        System.out.println(metaFieldDefaultRequest);

        MetaFieldModel createdMetaField = this.metaFieldServices.createDefaultMetaField(metaFieldDefaultRequest.functionMetadataModelId, metaFieldDefaultRequest.metaFieldDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdMetaField == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("META_FIELD ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW META_FIELD CREATED!");
        }
        apiResponse.setPayload(createdMetaField);

        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllMetaField() {
        List<MetaFieldModel> metaFieldModelList = this.metaFieldServices.fetchAllMetaField();

        ApiResponse apiResponse = new ApiResponse();
        if(metaFieldModelList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO META_FIELD EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL META_FIELDS!");
        }
        apiResponse.setPayload(metaFieldModelList);

        return apiResponse;
    }

    @GetMapping("/{metaFieldId}")
    public ApiResponse fetchById(@PathVariable Integer metaFieldId) {
        MetaFieldModel metaFieldModel = this.metaFieldServices.fetchByMetaFieldId(metaFieldId);

        ApiResponse apiResponse = new ApiResponse();
        if(metaFieldModel == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO META_FIELD EXIST WITH META_FIELD_ID: " + metaFieldId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("META_FIELD FOUND!");
        }
        apiResponse.setPayload(metaFieldModel);

        return apiResponse;
    }

    @PutMapping("/{metaFieldId}")
    public ApiResponse updateMetaField(@RequestBody MetaFieldModel metaFieldModel, @PathVariable Integer metaFieldId) {
        MetaFieldModel updatedMetaFieldModel = this.metaFieldServices.updateMetaField(metaFieldModel, metaFieldId);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedMetaFieldModel == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO META_FIELD EXIST WITH META_FIELD_ID: " + metaFieldId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("META_FIELD UPDATED!");
        }
        apiResponse.setPayload(updatedMetaFieldModel);

        return apiResponse;
    }

    @DeleteMapping("/{metaFieldTemplateId}")
    public ApiResponse deleteMetaFieldTemplate(@PathVariable Integer metaFieldTemplateId) {
        Integer deletedMetaFieldTemplateId = this.metaFieldServices.deleteMetaField(metaFieldTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedMetaFieldTemplateId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO META_FIELD EXIST WITH USER_ID: " + metaFieldTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("META_FIELD DELETED!");
        }
        apiResponse.setPayload(deletedMetaFieldTemplateId);

        return apiResponse;
    }
    
}
