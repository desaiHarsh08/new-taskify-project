package com.taskify.services;

import com.taskify.dtos.TaskDto;
import com.taskify.models.TaskModel;

import java.sql.Date;
import java.util.List;

public interface TaskServices {

    public TaskDto createTask(TaskDto taskDto);

    public List<TaskDto> fetchAllTasks();

    public List<TaskDto> fetchAllTaskDone();

    public TaskDto fetchByTaskId(Integer taskId);

    public void forwardTask(Integer taskId, String department);

    public void markTaskDone(Integer taskId, Integer taskFinishedByUserId, Date finishedDate);

    public TaskDto updateTask(TaskModel taskModel);

    public Integer deleteTask(Integer taskId);

}
