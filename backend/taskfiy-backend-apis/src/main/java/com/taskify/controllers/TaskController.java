package com.taskify.controllers;

import com.taskify.dtos.TaskDto;
import com.taskify.models.TaskModel;
import com.taskify.services.TaskServices;
import com.taskify.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

class TaskMarkDoneRequest {
    public Integer taskFinishedByUserId;
    public Date finishedDate;
}

class ForwardTaskRequest {
    public Integer taskId;
    public String department;
}

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    
    @Autowired
    private TaskServices taskServices;
    
    @PostMapping("/create")
    public ApiResponse createTask(@RequestBody TaskDto taskDto) {
        System.out.println(taskDto);
        TaskDto createdTaskDto = this.taskServices.createTask(taskDto);

        ApiResponse apiResponse = new ApiResponse();
        if(createdTaskDto == null) {
            apiResponse.setStatus(409);
            apiResponse.setHttpStatus(HttpStatus.CONFLICT);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("TASK ALREADY EXIST!");
        }
        else {
            apiResponse.setStatus(201);
            apiResponse.setHttpStatus(HttpStatus.CREATED);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("NEW TASK CREATED!");
        }
        apiResponse.setPayload(createdTaskDto);

        return apiResponse;
    }

    @PostMapping("/forward-task")
    public ApiResponse createTask(@RequestBody ForwardTaskRequest forwardTaskRequest) {
        this.taskServices.forwardTask(forwardTaskRequest.taskId, forwardTaskRequest.department);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setSuccess(false);
        apiResponse.setMessage("TASK FORWARDED!");

        apiResponse.setPayload(null);

        return apiResponse;
    }

    @GetMapping("")
    public ApiResponse fetchAllTaskTs() {
        List<TaskDto> TaskDtoList = this.taskServices.fetchAllTasks();

        ApiResponse apiResponse = new ApiResponse();
        if(TaskDtoList == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO TASK EXIST!");
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("ALL TASK!");
        }
        apiResponse.setPayload(TaskDtoList);

        return apiResponse;
    }

    @GetMapping("/{taskId}")
    public ApiResponse fetchById(@PathVariable Integer taskId) {
        TaskDto TaskDto = this.taskServices.fetchByTaskId(taskId);

        ApiResponse apiResponse = new ApiResponse();
        if(TaskDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage("NO Task EXIST WITH Task_ID: " + taskId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setMessage("Task FOUND!");
        }
        apiResponse.setPayload(TaskDto);

        return apiResponse;
    }

    @PostMapping("/mark-done/{taskId}")
    public ApiResponse markTaskDone(@PathVariable Integer taskId, @RequestBody TaskMarkDoneRequest taskMarkDoneRequest) {
        this.taskServices.markTaskDone(taskId, taskMarkDoneRequest.taskFinishedByUserId, taskMarkDoneRequest.finishedDate);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setMessage("TASK DONE!");
        apiResponse.setPayload(null);

        return apiResponse;
    }

    @PutMapping("/{taskId}")
    public ApiResponse updateTask(@RequestBody TaskModel taskTModel, @PathVariable Integer taskId) {
        TaskDto updatedTaskDto = this.taskServices.updateTask(taskTModel);

        ApiResponse apiResponse = new ApiResponse();

        if(updatedTaskDto == null) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO TASK EXIST WITH TASK_ID: " + taskId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("TASK UPDATED!");
        }
        apiResponse.setPayload(updatedTaskDto);

        return apiResponse;
    }

    @DeleteMapping("/{taskTId}")
    public ApiResponse deleteTaskT(@PathVariable Integer taskTId) {
        Integer deletedTaskTId = this.taskServices.deleteTask(taskTId);

        ApiResponse apiResponse = new ApiResponse();

        if(deletedTaskTId == -1) {
            apiResponse.setStatus(404);
            apiResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            apiResponse.setSuccess(false);
            apiResponse.setMessage("NO TASK EXIST WITH TASK_ID: " + taskTId);
        }
        else {
            apiResponse.setStatus(200);
            apiResponse.setHttpStatus(HttpStatus.OK);
            apiResponse.setSuccess(true);
            apiResponse.setMessage("TASK DELETED!");
        }
        apiResponse.setPayload(deletedTaskTId);

        return apiResponse;
    }
    
}
