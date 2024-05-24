package com.taskify.services.impl;

import com.taskify.dtos.FunctionDto;
import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.dtos.TaskDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.ActivityLogModel;
import com.taskify.models.TaskModel;
import com.taskify.models.UserModel;
import com.taskify.repositories.TaskRepository;
import com.taskify.repositories.TaskTemplateRepository;
import com.taskify.repositories.UserRepository;
import com.taskify.services.ActivityLogServices;
import com.taskify.services.FunctionServices;
import com.taskify.services.FunctionTemplateServices;
import com.taskify.services.TaskServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FunctionServices functionServices;

    @Autowired
    private FunctionTemplateServices functionTemplateServices;

    @Autowired
    private ActivityLogServices activityLogServices;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        // Create the task
        TaskModel taskModel = new TaskModel(
                0,
                this.taskTemplateRepository.findById(taskDto.getTaskTemplateModelId()).orElseThrow(() -> new ResourceNotFoundException("TaskTemplate", "id", taskDto.getTaskTemplateModelId())),
                this.userRepository.findById(taskDto.getTaskCreatedByUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", taskDto.getTaskCreatedByUserId())),
                taskDto.getTaskCreatedDate(),
                taskDto.getTaskPriority(),
                taskDto.getTaskAssignedToDepartment(),
                taskDto.getTaskAssignedToDepartmentDate(),
                false,
                taskDto.getTaskFinishedDate(),
                null
        );
        taskModel.setTaskAssignToUser(this.userRepository.findById(taskDto.getTaskAssignToUserId()).orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", taskDto.getTaskAssignToUserId())));
        Integer taskId = this.taskRepository.save(taskModel).getId();
        taskModel.setId(taskId);


        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "TASK",
                "CREATE",
                "New Task (#" + taskId + ") got created by " + taskModel.getTaskCreatedByUser().getName()
                );

        this.activityLogServices.createLog(activityLogModel);


        return this.fetchByTaskId(taskId);
    }

    @Override
    public List<TaskDto> fetchAllTasks() {
        List<TaskModel> taskModelList = this.taskRepository.findAll();

        List<TaskDto> taskDtoList = new ArrayList<>();

        for (TaskModel taskModel: taskModelList) {
            TaskDto taskDto = this.modelMapper.map(taskModel, TaskDto.class);

            taskDto.setTaskTemplateModelId(taskModel.getTaskTemplateModel().getId());
            taskDto.setTaskCreatedByUserId(taskModel.getTaskCreatedByUser().getId());
//            if(taskModel.getTaskFinishedByUser() != null) {
//                taskDto.setTaskFinishedByUserId(taskModel.getTaskFinishedByUser().getId());
//            }

            if(taskModel.getTaskAssignToUser() != null) {
                taskDto.setTaskAssignToUserId(taskModel.getTaskAssignToUser().getId());
            }


            taskDto.setFunctionDtoList(this.functionServices.fetchByTaskId(taskModel.getId()));

            taskDtoList.add(taskDto);
        }

        return taskDtoList;
    }

    @Override
    public List<TaskDto> fetchAllTaskDone() {
        List<TaskModel> taskModelList = this.taskRepository.findByIsTaskCompleted(true);

        List<TaskDto> taskDtoList = new ArrayList<>();

        for (TaskModel taskModel: taskModelList) {
            TaskDto taskDto = this.modelMapper.map(taskModel, TaskDto.class);

            taskDto.setTaskTemplateModelId(taskModel.getTaskTemplateModel().getId());
            taskDto.setTaskCreatedByUserId(taskModel.getTaskCreatedByUser().getId());
//            taskDto.setTaskFinishedByUserId(taskModel.getTaskFinishedByUser().getId());
            taskDto.setTaskFinishedByUserId(taskModel.getTaskAssignToUser().getId());

            taskDto.setFunctionDtoList(this.functionServices.fetchByTaskId(taskModel.getId()));

            taskDtoList.add(taskDto);
        }

        return taskDtoList;
    }

    @Override
    public TaskDto fetchByTaskId(Integer taskId) {
        TaskModel taskModel = this.taskRepository.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("TaskModel", "id", taskId));
        TaskDto taskDto = this.modelMapper.map(taskModel, TaskDto.class);
        if(taskDto == null) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }

        taskDto.setTaskTemplateModelId(taskModel.getTaskTemplateModel().getId());
        taskDto.setTaskCreatedByUserId(taskModel.getTaskCreatedByUser().getId());
//        if(taskModel.getTaskFinishedByUser() != null) {
//            taskDto.setTaskFinishedByUserId(taskModel.getTaskFinishedByUser().getId());
//        }
        if(taskModel.getTaskAssignToUser() != null) {
            taskDto.setTaskAssignToUserId(taskModel.getTaskAssignToUser().getId());
        }

        taskDto.setFunctionDtoList(this.functionServices.fetchByTaskId(taskId));

        return taskDto;
    }

    @Override
    public void markTaskDone(Integer taskId, Integer taskFinishedByUserId, Date finishedDate) {
        TaskModel taskModel = this.taskRepository.findById(taskId)
                .orElseThrow(()-> new ResourceNotFoundException("TaskModel", "id", taskId));

        UserModel userModel = this.userRepository.findById(taskFinishedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", taskFinishedByUserId));

        taskModel.setTaskCompleted(true);
//        taskModel.setTaskFinishedByUser(userModel);
        taskModel.setTaskFinishedDate(finishedDate);

        this.taskRepository.save(taskModel);

        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "TASK",
                "COMPLETE",
                "Task (#" + taskId + ") got completed."
        );

        this.activityLogServices.createLog(activityLogModel);
    }

    @Override
    public void forwardTask(Integer taskId, String department) {
        TaskModel taskModel = this.taskRepository.findById(taskId)
                .orElseThrow(()->new  ResourceNotFoundException("TaskModel", "id", taskId));

        taskModel.setTaskAssignedToDepartmentDate(new java.sql.Date(System.currentTimeMillis()));
        taskModel.setTaskAssignedToDepartment(department);

        this.taskRepository.save(taskModel);

    }
    @Override
    public TaskDto updateTask(TaskModel taskModel) {
        if(this.fetchByTaskId(taskModel.getId()) == null) {
            return null;
        }
        TaskDto taskDto =this.modelMapper.map(this.taskRepository.save(taskModel), TaskDto.class);

        taskDto.setTaskTemplateModelId(taskModel.getTaskTemplateModel().getId());
        taskDto.setTaskCreatedByUserId(taskModel.getTaskCreatedByUser().getId());
//        taskDto.setTaskFinishedByUserId(taskModel.getTaskFinishedByUser().getId());
        taskDto.setTaskAssignToUserId(taskModel.getTaskAssignToUser().getId());

        taskDto.setFunctionDtoList(this.functionServices.fetchByTaskId(taskDto.getId()));
        return taskDto;
    }

    @Override
    public Integer deleteTask(Integer taskId) {
        TaskModel taskModel = this.taskRepository.findById(taskId).orElseThrow(()->new ResourceNotFoundException("TaskModel", "id", taskId));

        if(this.fetchByTaskId(taskId) == null) {
            return -1;
        }
        // Delete the functionModel
        List<FunctionDto> functionDtoList = this.functionServices.fetchByTaskId(taskId);
        for (FunctionDto functionDto: functionDtoList) {
            this.functionServices.deleteFunction(functionDto.getId());
        }
        // Delete the task
        this.taskRepository.deleteById(taskId);


        // Create a log
        ActivityLogModel activityLogModel = new ActivityLogModel(
                0,
                new java.sql.Date(System.currentTimeMillis()),
                "TASK",
                "DELETE",
                "Task (#" + taskId + ") got deleted by " + taskModel.getTaskCreatedByUser().getName()
        );

        this.activityLogServices.createLog(activityLogModel);

        return taskId;
    }
}
