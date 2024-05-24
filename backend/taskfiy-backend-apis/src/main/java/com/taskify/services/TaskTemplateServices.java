package com.taskify.services;

import com.taskify.dtos.TaskTemplateDto;
import com.taskify.models.templates.TaskTemplateModel;

import java.util.List;

public interface TaskTemplateServices {

    public TaskTemplateDto createTaskTemplateModel(TaskTemplateDto taskTemplateDto);

    public List<TaskTemplateDto> fetchAllTaskTemplate();

    public TaskTemplateDto fetchById(Integer taskTemplateId);

    public TaskTemplateDto updateTaskTemplate(TaskTemplateModel taskTemplateModel, Integer taskTemplateId);

    public Integer deleteTaskTemplate(Integer taskTemplateId);
    
}
