package com.taskify.controllers;

import com.taskify.models.templates.MetaFieldTemplateModel;
import com.taskify.services.MetaFieldTemplateServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meta-field-templates")
public class MetaFieldTemplateController {
    
    @Autowired
    private MetaFieldTemplateServices metaFieldTemplateServices;

    @PostMapping("/create")
    public ApiResponse createMetaFieldTemplateModel(@RequestBody MetaFieldTemplateModel metaFieldTemplateModel) {
        MetaFieldTemplateModel createdMetaFieldTemplateModel = this.metaFieldTemplateServices.createMetaField(metaFieldTemplateModel);

        ApiResponse apiResponse = new ApiResponse();
        if(createdMetaFieldTemplateModel == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("META_FIELD_TEMPLATE ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW META_FIELD_TEMPLATE CREATED!");
        }
        apiResponse.setPayload(createdMetaFieldTemplateModel);

        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllMetaFieldTemplates() {
        List<MetaFieldTemplateModel> MetaFieldTemplateModels = this.metaFieldTemplateServices.fetchAllMetaFields();

        ApiResponse apiResponse = new ApiResponse();
        if(MetaFieldTemplateModels == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO META_FIELD_TEMPLATE EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL META_FIELD_TEMPLATES!");
        }
        apiResponse.setPayload(MetaFieldTemplateModels);

        return apiResponse;
    }

    @GetMapping("/{metaFieldTemplateId}")
    public ApiResponse fetchById(@PathVariable Integer metaFieldTemplateId) {
        MetaFieldTemplateModel MetaFieldTemplateModel = this.metaFieldTemplateServices.fetchById(metaFieldTemplateId);

        ApiResponse apiResponse = new ApiResponse();
        if(MetaFieldTemplateModel == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO META_FIELD_TEMPLATE EXIST WITH META_FIELD_TEMPLATE_ID: " + metaFieldTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("META_FIELD_TEMPLATE FOUND!");
        }
        apiResponse.setPayload(MetaFieldTemplateModel);

        return apiResponse;
    }

    @PutMapping("/{metaFieldTemplateId}")
    public ApiResponse updateMetaFieldTemplate(@RequestBody MetaFieldTemplateModel metaFieldTemplateModel, @PathVariable Integer metaFieldTemplateId) {
        MetaFieldTemplateModel updatedMetaFieldTemplate = this.metaFieldTemplateServices.updateMetaField(metaFieldTemplateModel, metaFieldTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedMetaFieldTemplate == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO META_FIELD_TEMPLATE EXIST WITH META_FIELD_TEMPLATE_ID: " + metaFieldTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("META_FIELD_TEMPLATE UPDATED!");
        }
        apiResponse.setPayload(updatedMetaFieldTemplate);

        return apiResponse;
    }

    @DeleteMapping("/{metaFieldTemplateId}")
    public ApiResponse deleteMetaFieldTemplate(@PathVariable Integer metaFieldTemplateId) {
        Integer deletedMetaFieldTemplateId = this.metaFieldTemplateServices.deleteMetaField(metaFieldTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedMetaFieldTemplateId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO META_FIELD_TEMPLATE EXIST WITH USER_ID: " + metaFieldTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("META_FIELD_TEMPLATE DELETED!");
        }
        apiResponse.setPayload(deletedMetaFieldTemplateId);

        return apiResponse;
    }
    
}
