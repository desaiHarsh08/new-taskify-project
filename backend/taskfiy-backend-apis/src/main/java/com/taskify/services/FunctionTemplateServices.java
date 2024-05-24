package com.taskify.services;

import com.taskify.dtos.FunctionTemplateDto;
import com.taskify.models.templates.FunctionTemplateModel;

import java.util.List;

public interface FunctionTemplateServices {

    public FunctionTemplateDto createFunctionTemplateModel(FunctionTemplateDto functionTemplateDto);

    public List<FunctionTemplateDto> fetchAllFunctionTemplate();

    public List<FunctionTemplateDto> fetchByTaskTemplateModelId(Integer taskTemplateModelId);

    public List<FunctionTemplateDto> fetchAllFunctionTemplateByDepartment(String functionDepartment);

    public FunctionTemplateDto fetchById(Integer functionTemplateId);

    public FunctionTemplateDto updateFunctionTemplate(FunctionTemplateDto functionTemplateDto, Integer functionTemplateId);

    public Integer deleteFunctionTemplate(Integer functionId);
    
}
