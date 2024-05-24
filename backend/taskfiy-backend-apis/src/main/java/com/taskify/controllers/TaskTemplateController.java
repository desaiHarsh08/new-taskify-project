package com.taskify.controllers;

import com.taskify.dtos.TaskTemplateDto;
import com.taskify.models.templates.TaskTemplateModel;
import com.taskify.services.TaskTemplateServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-templates")
public class TaskTemplateController {
    
    @Autowired
    private TaskTemplateServices taskTemplateServices;
    
    @PostMapping("/create")
    public ApiResponse createTaskTemplateModel(@RequestBody TaskTemplateDto taskTemplateDto) {
        TaskTemplateDto createdTaskTemplateDto = this.taskTemplateServices.createTaskTemplateModel(taskTemplateDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdTaskTemplateDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("TASK TEMPLATE ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW TASK TEMPLATE CREATED!");
        }
        apiResponse.setPayload(createdTaskTemplateDto);
        
        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllTaskTemplates() {
        List<TaskTemplateDto> taskTemplateDtoList = this.taskTemplateServices.fetchAllTaskTemplate();

        ApiResponse apiResponse = new ApiResponse();
        if(taskTemplateDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO TASK_TEMPLATE EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL TASK_TEMPLATES!");
        }
        apiResponse.setPayload(taskTemplateDtoList);

        return apiResponse;
    }

    @GetMapping("/{taskTemplateId}")
    public ApiResponse fetchById(@PathVariable Integer taskTemplateId) {
        TaskTemplateDto taskTemplateDto = this.taskTemplateServices.fetchById(taskTemplateId);

        ApiResponse apiResponse = new ApiResponse();
        if(taskTemplateDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO TASK_TEMPLATE EXIST WITH TASK_TEMPLATE_ID: " + taskTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("TASK_TEMPLATE FOUND!");
        }
        apiResponse.setPayload(taskTemplateDto);

        return apiResponse;
    }

    @PutMapping("/{taskTemplateId}")
    public ApiResponse updateTaskTemplate(@RequestBody TaskTemplateModel taskTemplateModel, @PathVariable Integer taskTemplateId) {
        TaskTemplateDto updatedTaskTemplateDto = this.taskTemplateServices.updateTaskTemplate(taskTemplateModel, taskTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedTaskTemplateDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO TASK_TEMPLATE EXIST WITH TASK_TEMPLATE_ID: " + taskTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("TASK_TEMPLATE UPDATED!");
        }
        apiResponse.setPayload(updatedTaskTemplateDto);

        return apiResponse;
    }

    @DeleteMapping("/{taskTemplateId}")
    public ApiResponse deleteTaskTemplate(@PathVariable Integer taskTemplateId) {
        Integer deletedTaskTemplateId = this.taskTemplateServices.deleteTaskTemplate(taskTemplateId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedTaskTemplateId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO TASK_TEMPLATE EXIST WITH USER_ID: " + taskTemplateId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("TASK_TEMPLATE DELETED!");
        }
        apiResponse.setPayload(deletedTaskTemplateId);

        return apiResponse;
    }
    
}
