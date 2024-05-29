package com.taskify.services.impl;

import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.dtos.TaskTemplateDto;
import com.taskify.exceptions.ResourceNotFoundException;
import com.taskify.models.templates.FunctionTemplateModel;
import com.taskify.models.templates.TaskTemplateModel;
import com.taskify.repositories.FunctionTemplateRepository;
import com.taskify.repositories.TaskTemplateRepository;
import com.taskify.services.FunctionTemplateServices;
import com.taskify.services.TaskTemplateServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskTemplateServicesImpl implements TaskTemplateServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private FunctionTemplateRepository functionTemplateRepository;

    @Autowired
    private FunctionTemplateServices functionTemplateServices;


    @Override
    public TaskTemplateDto createTaskTemplateModel(TaskTemplateDto taskTemplateDto) {

        System.out.println(taskTemplateDto);



        // Create the task template
        TaskTemplateModel createdTaskTemplateModel = this.taskTemplateRepository.save(this.modelMapper.map(taskTemplateDto, TaskTemplateModel.class));
        createdTaskTemplateModel = this.taskTemplateRepository.saveAndFlush(createdTaskTemplateModel);
        System.out.println("task created");

        taskTemplateDto.setId(createdTaskTemplateModel.getId());

        // Create the function templates
        for (FunctionTemplateDto functionTemplateDto: taskTemplateDto.getFunctionTemplateDtoList()) {
            System.out.println("creating function template:");
            functionTemplateDto.setTaskTemplateModelId(createdTaskTemplateModel.getId());

            FunctionTemplateDto createdFunctionTemplateDto = this.functionTemplateServices.createFunctionTemplateModel(functionTemplateDto);

            if(createdFunctionTemplateDto == null) { // If already exist then delete the task and return
                List<FunctionTemplateDto> functionTemplateDtoList = this.functionTemplateServices.fetchByTaskTemplateModelId(createdTaskTemplateModel.getId());
                for (FunctionTemplateDto f: functionTemplateDtoList) {
                    this.functionTemplateServices.deleteFunctionTemplate(f.getId());
                }
                this.deleteTaskTemplate(createdTaskTemplateModel.getId());
                return null;
            }

//            taskTemplateDto.getFunctionTemplateDtoList().add(createdFunctionTemplateDto);
        }



        return this.fetchById(createdTaskTemplateModel.getId());
    }

    @Override
    public List<TaskTemplateDto> fetchAllTaskTemplate() {
        List<TaskTemplateModel> taskTemplateModelList = this.taskTemplateRepository.findAll();

        List<TaskTemplateDto> taskTemplateDtoList = new ArrayList<>();

        for (TaskTemplateModel taskTemplateModel: taskTemplateModelList) {
            TaskTemplateDto taskTemplateDto = this.modelMapper.map(taskTemplateModel, TaskTemplateDto.class);

            taskTemplateDto.setFunctionTemplateDtoList(this.functionTemplateServices.fetchByTaskTemplateModelId(taskTemplateModel.getId()));

            taskTemplateDtoList.add(taskTemplateDto);

        }
        return taskTemplateDtoList;
    }

    @Override
    public TaskTemplateDto fetchById(Integer taskTemplateId) {
        TaskTemplateModel taskTemplateModel = this.taskTemplateRepository.findById(taskTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException("TaskTemplate", "id", taskTemplateId));

        TaskTemplateDto taskTemplateDto = this.modelMapper.map(taskTemplateModel, TaskTemplateDto.class);
        taskTemplateDto.setFunctionTemplateDtoList(this.functionTemplateServices.fetchByTaskTemplateModelId(taskTemplateId));;
        return taskTemplateDto;
    }

    @Override
    public TaskTemplateDto updateTaskTemplate(TaskTemplateModel taskTemplateModel, Integer taskTemplateId) {
        TaskTemplateModel existingTaskTemplateModel = this.modelMapper.map(this.fetchById(taskTemplateId), TaskTemplateModel.class);
        if(existingTaskTemplateModel == null) {
            return null;
        }
        this.taskTemplateRepository.save(taskTemplateModel);
        return this.fetchById(taskTemplateId);
    }

    @Override
    public Integer deleteTaskTemplate(Integer taskTemplateId) {
        TaskTemplateModel existingTaskTemplateModel = this.modelMapper.map(this.fetchById(taskTemplateId), TaskTemplateModel.class);
        if(existingTaskTemplateModel == null) {
            return -1;
        }
        // Delete all the function template
        List<FunctionTemplateModel> functionTemplateModelList = this.functionTemplateRepository.findByTaskTemplateModelId(taskTemplateId);
        for (FunctionTemplateModel functionTemplateModel: functionTemplateModelList) {
            this.functionTemplateServices.deleteFunctionTemplate(functionTemplateModel.getId());
        }

        // Delete the task template
        this.taskTemplateRepository.deleteById(taskTemplateId);
        return taskTemplateId;
    }
}
