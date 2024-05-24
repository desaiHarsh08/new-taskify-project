package com.taskify.services;


import com.taskify.dtos.FunctionDto;
import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.models.FunctionModel;

import java.sql.Date;
import java.util.List;

public interface FunctionServices {

    public FunctionDto createFunction(FunctionDto functionDto, FunctionTemplateDto functionTemplateDto);

    public FunctionDto createDefaultFunction(FunctionDto functionDto, FunctionTemplateDto functionTemplateDto);

    public List<FunctionDto> fetchAllFunctions();

    public List<FunctionDto> fetchAllFunctionDone();

    public FunctionDto fetchByFunctionId(Integer functionId);

    public void markFunctionDone(Integer functionId, Integer functionFinishedByUserId, Date finishedDate);

    public List<FunctionDto> fetchByTaskId(Integer taskId);

    public FunctionDto updateFunction(FunctionModel functionModel);

    public Integer deleteFunction(Integer functionId);
    
}
